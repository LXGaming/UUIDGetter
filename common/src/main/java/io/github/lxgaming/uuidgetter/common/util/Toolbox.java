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

package io.github.lxgaming.uuidgetter.common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;

import java.util.Optional;

public class Toolbox {
    
    public static final Gson GSON = new GsonBuilder()
            .disableHtmlEscaping()
            .enableComplexMapKeySerialization()
            .setPrettyPrinting()
            .create();
    
    public static boolean isValidUsername(String username) {
        return username != null && username.matches("[a-zA-Z0-9_]{3,16}");
    }
    
    public static <T> Optional<T> parseJson(JsonElement jsonElement, Class<T> type) {
        try {
            return Optional.of(GSON.fromJson(jsonElement, type));
        } catch (JsonParseException ex) {
            return Optional.empty();
        }
    }
    
    public static <T> Optional<T> parseJson(JsonReader jsonReader, Class<T> type) {
        try {
            return Optional.of(GSON.fromJson(jsonReader, type));
        } catch (JsonParseException ex) {
            return Optional.empty();
        }
    }
    
    public static <T> Optional<T> parseJson(String json, Class<T> type) {
        try {
            return Optional.of(GSON.fromJson(json, type));
        } catch (JsonParseException ex) {
            return Optional.empty();
        }
    }
}