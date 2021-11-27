package com.am2.eshopapp.Entities;

import android.content.Context;
import android.content.SharedPreferences;

import com.am2.eshopapp.R;

public class SharedPreferenceEntities {
    private static Context context;
    private static String email, rol, name;

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        SharedPreferenceEntities.context = context;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        SharedPreferenceEntities.email = email;
    }

    public static String getRol() {
        return rol;
    }

    public static void setRol(String rol) {
        SharedPreferenceEntities.rol = rol;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        SharedPreferenceEntities.name = name;
    }

    public static void guardarPreferecia(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(getContext()
                .getText(R.string.Preference_key).toString(),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (!getEmail().isEmpty()){
            editor.putString("email", getEmail());
        }

        editor.commit();
    }

    public static String leerPreferencia(int interador){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(getContext()
                .getText(R.string.Preference_key).toString(),Context.MODE_PRIVATE);
        switch (interador){
            case 1:
                SharedPreferenceEntities.name = sharedPreferences.getString("name", "");
                return getName();

            case 2:
                SharedPreferenceEntities.email = sharedPreferences.getString("email", "");
                return getEmail();

            case 3:
                SharedPreferenceEntities.rol = sharedPreferences.getString("rol", "");
                return getRol();

            default:return "";
        }
    }

    public static void limpiarPreferencia(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(getContext()
                .getText(R.string.Preference_key).toString(),Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
    }
}
