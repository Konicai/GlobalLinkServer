/*
 * Copyright (c) 2021-2023 GeyserMC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * @author GeyserMC
 * @link https://github.com/GeyserMC/GlobalLinkServer
 */
package org.geysermc.globallinkserver.java;

import com.github.steveice10.mc.auth.data.GameProfile;
import com.github.steveice10.mc.protocol.packet.ingame.clientbound.ClientboundSystemChatPacket;
import com.github.steveice10.mc.protocol.packet.login.clientbound.ClientboundLoginDisconnectPacket;
import com.github.steveice10.packetlib.Session;
import java.util.UUID;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.geysermc.globallinkserver.player.Player;

public class JavaPlayer implements Player {
    private static final LegacyComponentSerializer LEGACY_SERIALIZER = LegacyComponentSerializer.legacyAmpersand();

    private final Session session;
    private final GameProfile profile;

    private int linkId;

    public JavaPlayer(Session session, GameProfile profile) {
        this.session = session;
        this.profile = profile;
    }

    @Override
    public void sendMessage(String message) {
        session.send(new ClientboundSystemChatPacket(LEGACY_SERIALIZER.deserialize(message), false));
    }

    @Override
    public void disconnect(String reason) {
        session.send(new ClientboundLoginDisconnectPacket(jsonFormatMessage(reason)));
        session.disconnect(reason);
    }

    @Override
    public UUID uniqueId() {
        return profile.getId();
    }

    @Override
    public String username() {
        return profile.getName();
    }

    @Override
    public int linkId() {
        return linkId;
    }

    @Override
    public void linkId(int linkId) {
        this.linkId = linkId;
    }
}
