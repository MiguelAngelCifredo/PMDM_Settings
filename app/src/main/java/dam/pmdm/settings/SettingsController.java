package dam.pmdm.settings;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SettingsController {

    private final SharedPreferences pref;
    private final Context context;

    public SettingsController(Context context) {
        this.context = context;
        this.pref = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
    }

    // El triple punto (...) permite pasar cero, uno o muchos argumentos
    private String getString(int resId, Object... formatArgs) {
        return context.getString(resId, formatArgs);
    }

    public void preferencesDelete() {
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.apply();
    }

    // --- SECCIÓN: RELAX ---

    public String getVal_relax_descansar() {
        return pref.getString(getString(R.string.key_relax_descansar), getString(R.string.def_relax_descansar));
    }

    @NonNull
    public String getStr_relax_descansar() {
        String val = getVal_relax_descansar();
        int horas = Integer.parseInt(val);
        String resultado = horas + " " + (horas == 1 ? getString(R.string.val_hora) : getString(R.string.val_horas));
        return getString(R.string.lb_relax_descansar, resultado);
    }

    public String getVal_relax_nombreAlarma() {
        return pref.getString(getString(R.string.key_relax_nombreAlarma), getString(R.string.def_relax_nombreAlarma));
    }

    @NonNull
    public String getStr_relax_nombreAlarma() {
        return getString(R.string.lb_relax_nombreAlarma, getVal_relax_nombreAlarma());
    }

    // --- SECCIÓN: PANTALLA ---

    public String getVal_pantalla_apagar() {
        return pref.getString(getString(R.string.key_pantalla_apagar), getString(R.string.def_pantalla_apagar));
    }

    @NonNull
    public String getStr_pantalla_apagar() {
        String val = getVal_pantalla_apagar();
        // Si val es "0", el resultado visual es "Nunca" (sacado de recursos)
        // Si es otro (1, 2, 10), le añadimos el sufijo " min"
        String resultado = val.equals(getString(R.string.def_pantalla_apagar))
                ? getString(R.string.nunca)
                : val + " " + getString(R.string.val_min);
        return getString(R.string.lb_pantalla_apagar, resultado);
    }

    public int getVal_pantalla_tamanoFuente() {
        return pref.getInt(getString(R.string.key_pantalla_tamanioFuente),
                context.getResources().getInteger(R.integer.def_pantalla_tamanioFuente));
    }

    @NonNull
    public String getStr_pantalla_tamanoFuente() {
        return getString(R.string.lb_pantalla_tamanoFuente, getVal_pantalla_tamanoFuente());
    }

    public boolean getVal_pantalla_modoObscuro() {
        return pref.getBoolean(getString(R.string.key_pantalla_modoObscuro), false);
    }

    @NonNull
    public String getStr_pantalla_modoObscuro() {
        String estado = getVal_pantalla_modoObscuro() ? getString(R.string.val_ON) : getString(R.string.val_OFF);
        return getString(R.string.lb_pantalla_modoObscuro, estado);
    }

    // --- SECCIÓN: CONEXIÓN ---

    public Set<String> getVal_conn_limitarDatos() {
        return pref.getStringSet(getString(R.string.key_conn_limitarDatos), new HashSet<>());
    }

    @NonNull
    public String getStr_conn_limitarDatos() {
        Set<String> valoresSeleccionados = getVal_conn_limitarDatos();
        String resultado;
        if (valoresSeleccionados != null && !valoresSeleccionados.isEmpty()) {
            List<String> etiquetas = new ArrayList<>();
            for (String val : valoresSeleccionados) {
                switch (val) {
                    case "1" -> etiquetas.add(getString(R.string.val1_conn_limitarDatos));
                    case "2" -> etiquetas.add(getString(R.string.val2_conn_limitarDatos));
                    case "3" -> etiquetas.add(getString(R.string.val3_conn_limitarDatos));
                    default  -> etiquetas.add(val);
                }
            }
            // Unimos los elementos con comas para la interfaz
            resultado = String.join(", ", etiquetas);
        } else {
            // Si el Set está vacío, mostramos el texto por defecto ("No definido")
            resultado = getString(R.string.val_NULL);
        }

        return getString(R.string.lb_conn_limitarDatos, resultado);
    }

    public Set<String> getVal_conn_calidadDescargas() {
        return pref.getStringSet(getString(R.string.key_conn_descargarVideo), new HashSet<>());
    }

    @NonNull
    public String getStr_conn_calidadDescargas() {
        Set<String> val = getVal_conn_calidadDescargas();
        String resultado;
        if (val != null && !val.isEmpty()) {
            List<String> textosSeleccionados = new ArrayList<>();
            for (String valor : val) {
                switch (valor) {
                    case "480"  -> textosSeleccionados.add("480p");
                    case "1080" -> textosSeleccionados.add("1080p");
                    case "1"    -> textosSeleccionados.add("HD");
                    case "0"    -> textosSeleccionados.add(getString(R.string.original));
                    default     -> textosSeleccionados.add(valor);
                }
            }
            resultado = String.join(", ", textosSeleccionados);
        } else {
            resultado = getString(R.string.val_NULL);
        }
        return getString(R.string.lb_conn_calidadDescargas, resultado);
    }

    public boolean getVal_conn_modoRestrictivo() {
        return pref.getBoolean(getString(R.string.key_conn_modoRestrictivo), false);
    }

    @NonNull
    public String getStr_conn_modoRestrictivo() {
        String estado = getVal_conn_modoRestrictivo() ? getString(R.string.val_SI) : getString(R.string.val_NO);
        return getString(R.string.lb_conn_modoRestrictivo, estado);
    }

    public boolean getVal_conn_estadisticasUso() {
        return pref.getBoolean(getString(R.string.key_conn_estadisticasUso), false);
    }

    @NonNull
    public String getStr_conn_estadisticasUso() {
        String estado = getVal_conn_estadisticasUso() ? getString(R.string.val_ON) : getString(R.string.val_OFF);
        return getString(R.string.lb_conn_estadisticasUso, estado);
    }

}