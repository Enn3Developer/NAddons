package com.enn3developer.naddons.mixins;

import com.google.gson.Gson;
import dan200.computercraft.ComputerCraft;
import dan200.computercraft.shared.util.IDAssigner;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Map;

@Mixin(IDAssigner.class)
public class IDAssignerMixin {
    @Final
    @Shadow(remap = false)
    private static Gson GSON;
    @Shadow(remap = false)
    private Map<String, Integer> ids;
    @Final
    @Shadow(remap = false)
    private Path idFile;
    @Unique
    private Path nAddons$newIdFile;

    @Shadow(remap = false)
    private Map<String, Integer> loadIds() {
        return null;
    }

    @Inject(method = "<init>", at = @At(value = "TAIL"), remap = false)
    public void init(Path path, CallbackInfo ci) {
        nAddons$newIdFile = path.resolveSibling(path.getFileName() + ".new");
    }

    /**
     * @author SquidDev
     * @reason Ensure atomic writes
     */
    @Overwrite(remap = false)
    public synchronized int getNextId(String kind) {
        if (ids == null) ids = loadIds();

        var existing = ids.get(kind);
        var next = existing == null ? 0 : existing + 1;
        ids.put(kind, next);

        // We've changed the ID file, so save it back again. We save to a temporary ".new" file, then move that over the
        // original. This should reduce the risk of corrupting the file if Minecraft (or the computer!) is stopped.
        try {
            try (var channel = FileChannel.open(nAddons$newIdFile, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)) {
                Writer writer = new BufferedWriter(Channels.newWriter(channel, StandardCharsets.UTF_8));
                GSON.toJson(ids, writer);
                writer.flush();

                channel.force(false);
            }

            try {
                Files.move(nAddons$newIdFile, idFile, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            } catch (AtomicMoveNotSupportedException | UnsupportedOperationException e) {
                Files.move(nAddons$newIdFile, idFile, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            ComputerCraft.log.error("Cannot update ID file '{}'", idFile, e);
        }

        return next;
    }
}
