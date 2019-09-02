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

package io.github.lxgaming.uuidgetter.common;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import io.github.lxgaming.uuidgetter.common.util.Logger;
import io.github.lxgaming.uuidgetter.common.util.Toolbox;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class UUIDGetter {
    
    private static UUIDGetter instance;
    private Logger logger;
    
    public UUIDGetter() {
        instance = this;
        this.logger = new Logger();
    }
    
    public String getOfflineUUID(String username) {
        return UUID.nameUUIDFromBytes(("OfflinePlayer:" + username).getBytes(StandardCharsets.UTF_8)).toString();
    }
    
    public String getOnlineUUID(String username) {
        try {
            URL url = new URL(String.format("https://api.mojang.com/users/profiles/minecraft/%s", username));
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            
            int statusCode = urlConnection.getResponseCode();
            // No Content
            if (statusCode == 204) {
                return null;
            }
            
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {
                return Toolbox.parseJson(new JsonReader(reader), JsonObject.class)
                        .flatMap(jsonObject -> Toolbox.parseJson(jsonObject.get("id"), String.class))
                        .map(string -> string.replaceAll("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5"))
                        .orElse("Unknown");
            }
        } catch (Exception ex) {
            getLogger().error("Encountered an error while getting UUID for {}: {}", username, ex.getMessage());
            return "Unknown";
        }
    }
    
    public static UUIDGetter getInstance() {
        return instance;
    }
    
    public Logger getLogger() {
        return logger;
    }
}