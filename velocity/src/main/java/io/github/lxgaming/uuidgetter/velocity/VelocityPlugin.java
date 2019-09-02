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

package io.github.lxgaming.uuidgetter.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import io.github.lxgaming.uuidgetter.common.UUIDGetter;
import io.github.lxgaming.uuidgetter.common.util.Logger;
import io.github.lxgaming.uuidgetter.common.util.Reference;
import io.github.lxgaming.uuidgetter.velocity.command.GetUUIDCommand;
import org.slf4j.LoggerFactory;

@Plugin(
        id = Reference.ID,
        name = Reference.NAME,
        version = Reference.VERSION,
        description = Reference.DESCRIPTION,
        url = Reference.WEBSITE,
        authors = {Reference.AUTHORS}
)
public class VelocityPlugin {
    
    private static VelocityPlugin instance;
    
    @Inject
    private ProxyServer proxy;
    
    @Subscribe
    public void onProxyInitialize(ProxyInitializeEvent event) {
        instance = this;
        UUIDGetter uuidGetter = new UUIDGetter();
        uuidGetter.getLogger()
                .add(Logger.Level.INFO, LoggerFactory.getLogger(Reference.NAME)::info)
                .add(Logger.Level.WARN, LoggerFactory.getLogger(Reference.NAME)::warn)
                .add(Logger.Level.ERROR, LoggerFactory.getLogger(Reference.NAME)::error)
                .add(Logger.Level.DEBUG, LoggerFactory.getLogger(Reference.NAME)::debug);
        
        getProxy().getCommandManager().register(new GetUUIDCommand(), "getuuid");
        uuidGetter.getLogger().info("{} v{} has loaded", Reference.NAME, Reference.VERSION);
    }
    
    public static VelocityPlugin getInstance() {
        return instance;
    }
    
    public ProxyServer getProxy() {
        return proxy;
    }
}