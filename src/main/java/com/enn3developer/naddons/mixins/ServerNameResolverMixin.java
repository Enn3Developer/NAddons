package com.enn3developer.naddons.mixins;

import com.enn3developer.naddons.utils.Utils;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.client.multiplayer.resolver.ServerNameResolver;
import net.minecraft.client.multiplayer.resolver.ServerRedirectHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;
import java.util.Optional;

@Mixin(ServerNameResolver.class)
public class ServerNameResolverMixin {
    @Redirect(method = "resolveAddress", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/resolver/ServerRedirectHandler;lookupRedirect(Lnet/minecraft/client/multiplayer/resolver/ServerAddress;)Ljava/util/Optional;"))
    public Optional<ServerAddress> redirectResolve(ServerRedirectHandler instance, ServerAddress serverAddress) {
        System.out.println("N srv changed");
        InitialDirContext dirContext;
        try {
            Class.forName("com.sun.jndi.dns.DnsContextFactory");
            Hashtable<String, String> hashtable = new Hashtable<>();
            hashtable.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
            hashtable.put("java.naming.provider.url", "dns:");
            hashtable.put("com.sun.jndi.dns.timeout.retries", "1");
            dirContext = new InitialDirContext(hashtable);
        } catch (Throwable ignored) {
            return Optional.empty();
        }

        if (serverAddress.getPort() == 25565) {
            try {
                Attributes attributes = dirContext.getAttributes("_rgbcraft._tcp." + serverAddress.getHost(), new String[]{"SRV"});
                Attribute srv = attributes.get("srv");
                if (srv != null) {
                    System.out.println("N srv: " + srv.get());
                    String[] data = srv.get().toString().split(" ", 4);
                    return Optional.of(new ServerAddress(data[3], Utils.parsePort(data[2])));
                }
            } catch (Throwable ignored) {
            }
        }

        return Optional.empty();
    }
}
