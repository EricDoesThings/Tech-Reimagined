package com.techreimagined.api;

import com.techreimagined.api.exceptions.CoreInaccessibleException;

import java.lang.reflect.Field;

/* You are free to:
 * 
 * Share — copy and redistribute the material in any medium or format
 * Adapt — remix, transform, and build upon the material
 * for any purpose, even commercially.
 * The licensor cannot revoke these freedoms as long as you follow the license terms.
 * Under the following terms:

 * Attribution — You must give appropriate credit, provide a link to the license, and indicate if changes were made. You may do so in any reasonable manner, but not in any way that suggests the licensor endorses you or your use.
 * ShareAlike — If you remix, transform, or build upon the material, you must distribute your contributions under the same license as the original.
 * No additional restrictions — You may not apply legal terms or technological measures that legally restrict others from doing anything the license permits.
 * Notices:
 * 
 * You do not have to comply with the license for elements of the material in the public domain or where your use is permitted by an applicable exception or limitation.
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */
public enum TechReimaginedApi {
    ;

    private static final String CORE_API_FQN = "com.techreimagined.techreimagined.common.core.Api";
    private static final String CORE_API_FIELD = "INSTANCE";
    private static final ITechReimaginedApi HELD_API;

    static {
        try {
            final Class<?> apiClass = Class.forName(CORE_API_FQN);
            final Field apiField = apiClass.getField(CORE_API_FIELD);

            HELD_API = (ITechReimaginedApi) apiField.get(apiClass);
        } catch (ClassNotFoundException ex) {
            throw new CoreInaccessibleException("Tech Reimagined API tried to access the " + CORE_API_FQN + " class, without it being declared");
        } catch (NoSuchFieldException ex) {
            throw new CoreInaccessibleException("Tech Reimagined API tried to access the " + CORE_API_FIELD + " field in " + CORE_API_FQN + " without it being declared");
        } catch (IllegalAccessException ex) {
            throw new CoreInaccessibleException("Tech Reimagined API tried to access the " + CORE_API_FIELD + " field in " + CORE_API_FQN + " without enough access permissions");
        }
    }

    public static ITechReimaginedApi instance() {
        return HELD_API;
    }
}
