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

package io.github.lxgaming.uuidgetter.bungee;

import io.github.lxgaming.uuidgetter.bungee.command.GetUUIDCommand;
import io.github.lxgaming.uuidgetter.common.UUIDGetter;
import io.github.lxgaming.uuidgetter.common.util.Logger;
import io.github.lxgaming.uuidgetter.common.util.Reference;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeePlugin extends Plugin {
    
    private static BungeePlugin instance;
    
    @Override
    public void onEnable() {
        instance = this;
        UUIDGetter uuidGetter = new UUIDGetter();
        uuidGetter.getLogger()
                .add(Logger.Level.INFO, getLogger()::info)
                .add(Logger.Level.WARN, getLogger()::warning)
                .add(Logger.Level.ERROR, getLogger()::severe)
                .add(Logger.Level.DEBUG, getLogger()::fine);
        
        getProxy().getPluginManager().registerCommand(getInstance(), new GetUUIDCommand());
        uuidGetter.getLogger().info("{} v{} has loaded", Reference.NAME, Reference.VERSION);
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
    }
    
    public static BungeePlugin getInstance() {
        return instance;
    }
}