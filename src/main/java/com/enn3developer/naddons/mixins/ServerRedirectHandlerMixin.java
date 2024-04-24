package com.enn3developer.naddons.mixins;

import com.enn3developer.naddons.utils.Utils;
import com.mojang.logging.LogUtils;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.client.multiplayer.resolver.ServerRedirectHandler;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;
import java.util.Optional;

@Mixin(ServerRedirectHandler.class)
public interface ServerRedirectHandlerMixin {
    @Shadow
    Logger LOGGER = LogUtils.getLogger();
    @Shadow
    ServerRedirectHandler EMPTY = (p_171897_) -> Optional.empty();

    /**
     * @author Enn3Developer
     * @reason Changed _minecraft
     */
    @Overwrite
    static ServerRedirectHandler createDnsSrvRedirectHandler() {
        InitialDirContext $$4;
        try {
            String $$0 = "com.sun.jndi.dns.DnsContextFactory";
            Class.forName($$0);
            Hashtable<String, String> $$1 = new Hashtable<>();
            $$1.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
            $$1.put("java.naming.provider.url", "dns:");
            $$1.put("com.sun.jndi.dns.timeout.retries", "1");
            $$4 = new InitialDirContext($$1);
        } catch (Throwable var3) {
            LOGGER.error("Failed to initialize SRV redirect resolved, some servers might not work", var3);
            return EMPTY;
        }

        return (p_171900_) -> {
            if (p_171900_.getPort() == 25565) {
                try {
                    Attributes $$2 = $$4.getAttributes("_rgbcraft._tcp." + p_171900_.getHost(), new String[]{"SRV"});
                    Attribute $$3 = $$2.get("srv");
                    if ($$3 != null) {
                        String[] $$4x = $$3.get().toString().split(" ", 4);
                        return Optional.of(new ServerAddress($$4x[3], Utils.parsePort($$4x[2])));
                    }
                } catch (Throwable ignored) {
                }
            }

            return Optional.empty();
        };
    }
}
