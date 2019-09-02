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

package io.github.lxgaming.uuidgetter.bungee.command;

import com.google.common.collect.Lists;
import io.github.lxgaming.uuidgetter.bungee.util.BungeeToolbox;
import io.github.lxgaming.uuidgetter.common.UUIDGetter;
import io.github.lxgaming.uuidgetter.common.util.Toolbox;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Command;

import java.util.List;

public class GetUUIDCommand extends Command {
    
    public GetUUIDCommand() {
        super("getuuid", "uuidgetter.base");
    }
    
    @Override
    public void execute(CommandSender sender, String[] args) {
        List<String> arguments = Lists.newArrayList(args);
        if (arguments.isEmpty()) {
            sender.sendMessage(BungeeToolbox.getPluginInformation().create());
            return;
        }
        
        if (arguments.size() > 1) {
            sender.sendMessage(BungeeToolbox.getTextPrefix()
                    .append("Invalid Arguments: /getuuid <username>").color(ChatColor.RED)
                    .create());
            return;
        }
        
        String username = arguments.remove(0);
        if (!Toolbox.isValidUsername(username)) {
            sender.sendMessage(BungeeToolbox.getTextPrefix()
                    .append(username).color(ChatColor.YELLOW)
                    .append(" is not a valid username").color(ChatColor.RED)
                    .create());
            return;
        }
        
        String offlineUniqueId = UUIDGetter.getInstance().getOfflineUUID(username);
        String onlineUniqueId = UUIDGetter.getInstance().getOnlineUUID(username);
        sender.sendMessage(new ComponentBuilder("")
                .append(username).color(ChatColor.YELLOW)
                .append("\n")
                .append("    Online").color(ChatColor.AQUA).append(": ").color(ChatColor.WHITE)
                .append(onlineUniqueId).color(ChatColor.GREEN).event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, onlineUniqueId))
                .append("\n", ComponentBuilder.FormatRetention.NONE)
                .append("    Offline").color(ChatColor.AQUA).append(": ").color(ChatColor.WHITE)
                .append(offlineUniqueId).color(ChatColor.RED).event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, offlineUniqueId))
                .create());
    }
}