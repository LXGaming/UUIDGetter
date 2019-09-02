/*
 * Copyright 2019 Alex Thomson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.lxgaming.uuidgetter.velocity.command;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.velocitypowered.api.command.Command;
import com.velocitypowered.api.command.CommandSource;
import io.github.lxgaming.uuidgetter.common.UUIDGetter;
import io.github.lxgaming.uuidgetter.common.util.Toolbox;
import io.github.lxgaming.uuidgetter.velocity.util.VelocityToolbox;
import net.kyori.text.TextComponent;
import net.kyori.text.event.ClickEvent;
import net.kyori.text.format.TextColor;

import java.util.List;

public class GetUUIDCommand implements Command {
    
    @Override
    public void execute(CommandSource source, String[] args) {
        List<String> arguments = Lists.newArrayList(args);
        if (arguments.isEmpty()) {
            source.sendMessage(VelocityToolbox.getPluginInformation());
            return;
        }
        
        if (arguments.size() > 1) {
            source.sendMessage(VelocityToolbox.getTextPrefix()
                    .append(TextComponent.of("Invalid Arguments: /getuuid <username>", TextColor.RED)));
            return;
        }
        
        String username = arguments.remove(0);
        if (!Toolbox.isValidUsername(username)) {
            source.sendMessage(VelocityToolbox.getTextPrefix()
                    .append(TextComponent.of(username, TextColor.YELLOW))
                    .append(TextComponent.of(" is not a valid username", TextColor.RED)));
            return;
        }
        
        String offlineUniqueId = UUIDGetter.getInstance().getOfflineUUID(username);
        String onlineUniqueId = UUIDGetter.getInstance().getOnlineUUID(username);
        source.sendMessage(TextComponent.builder("")
                .append(TextComponent.of(username, TextColor.YELLOW))
                .append(TextComponent.newline())
                .append(TextComponent.of("    Online", TextColor.AQUA)).append(TextComponent.of(": ", TextColor.WHITE))
                .append(TextComponent.of(onlineUniqueId, TextColor.GREEN).clickEvent(ClickEvent.suggestCommand(onlineUniqueId)))
                .append(TextComponent.newline())
                .append(TextComponent.of("    Offline", TextColor.AQUA)).append(TextComponent.of(": ", TextColor.WHITE))
                .append(TextComponent.of(offlineUniqueId, TextColor.RED).clickEvent(ClickEvent.suggestCommand(offlineUniqueId)))
                .build());
    }
    
    @Override
    public List<String> suggest(CommandSource source, String[] currentArgs) {
        return ImmutableList.of();
    }
    
    @Override
    public boolean hasPermission(CommandSource source, String[] args) {
        return source.hasPermission("uuidgetter.base");
    }
}