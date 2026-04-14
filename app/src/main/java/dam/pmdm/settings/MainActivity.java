package dam.pmdm.settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        setSupportActionBar(findViewById(R.id.myToolBar));

        pref = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mnu_ppal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        boolean valorRetorno = super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.mnuDelete) {
            confirmarBorrado();
            valorRetorno = true;
        }
        if (item.getItemId() == R.id.mnuSettings) {
            preferencesOpen();
            valorRetorno = true;
        }
        return valorRetorno;
    }

    @Override
    protected void onResume() {
        super.onResume();
        preferencesShow();
    }

    private void preferencesLog(String name, Object value) {
        Log.d("SETTINGS_PREFERENCES", "name=" + name + " value=" + value.toString());
    }

    private void preferencesShow() {
        // --- SECCIÓN: RELAX ---
        ((TextView) findViewById(R.id.lb_relax_descansar)).setText(getPref_relax_descansar());
        ((TextView) findViewById(R.id.lb_relax_nombreAlarma)).setText(getPref_relax_nombreAlarma());

        // --- SECCIÓN: PANTALLA ---
        ((TextView) findViewById(R.id.lb_pantalla_apagar)).setText(getPref_pantalla_apagar());
        ((TextView) findViewById(R.id.lb_pantalla_tamanoFuente)).setText(getPref_pantalla_tamanoFuente());
        ((TextView) findViewById(R.id.lb_pantalla_modoObscuro)).setText(getPref_pantalla_modoObscuro());

        // --- SECCIÓN: CONEXIÓN ---
        ((TextView) findViewById(R.id.lb_conn_limitarDatos)).setText(getPref_conn_limitarDatos());
        ((TextView) findViewById(R.id.lb_conn_calidadDescargas)).setText(getPref_conn_calidadDescargas());
        ((TextView) findViewById(R.id.lb_conn_modoRestrictivo)).setText(getPref_conn_modoRestrictivo());
        ((TextView) findViewById(R.id.lb_conn_estadisticasUso)).setText(getPref_conn_estadisticasUso());
    }

    private void confirmarBorrado() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.tit_confimDelete)
                .setMessage(R.string.msg_confimDelete)
                .setIcon(R.drawable.ic_warning)
                .setPositiveButton(R.string.pos_confimDelete, (dialog, which) -> {
                    preferencesDelete();
                    preferencesShow();
                    Toast.makeText(this, R.string.tst_confimDelete, Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton(R.string.neg_confimDelete, null)
                .show();
    }

    private void preferencesDelete() {
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.apply();
    }

    private void preferencesOpen() {
       Intent i = new Intent(this, SettingsActivity.class);
       startActivity(i);
    }

    @NonNull
    private String getPref_relax_descansar() { // ListPreference (Devuelve String)
        String valorVisual;
        String txt;
        String PREF_relax_descansar = pref.getString(getString(R.string.key_relax_descansar), getString(R.string.val_NULL));
        preferencesLog(getString(R.string.key_relax_descansar), PREF_relax_descansar);
        if (PREF_relax_descansar.equals(getString(R.string.val_NULL))) {   // Caso: No establecido.
            valorVisual = PREF_relax_descansar;
        } else {   // Caso: Hay un número.
            int horas = Integer.parseInt(PREF_relax_descansar);
            valorVisual = horas + " " + (horas == 1 ? getString(R.string.val_hora) : getString(R.string.val_horas));
        }
        txt = getString(R.string.lb_relax_descansar, valorVisual);
        return txt;
    }

    @NonNull
    private String getPref_relax_nombreAlarma() { // EditTextPreference (Devuelve String)
        String txt;
        String PREF_relax_nombreAlarma = pref.getString(getString(R.string.key_relax_nombreAlarma),
                getString(R.string.def_relax_nombreAlarma));
        preferencesLog(getString(R.string.key_relax_nombreAlarma), PREF_relax_nombreAlarma);
        txt = getString(R.string.lb_relax_nombreAlarma, PREF_relax_nombreAlarma);
        return txt;
    }

    @NonNull
    private String getPref_pantalla_apagar() { // ListPreference (Devuelve String)
        String valorVisual;
        String txt;
        String PREF_pantalla_apagar = pref.getString(getString(R.string.key_pantalla_apagar), getString(R.string.nunca));
        preferencesLog(getString(R.string.key_pantalla_apagar), PREF_pantalla_apagar);
        if (PREF_pantalla_apagar.equals(getString(R.string.nunca))) {   // Caso: No establecido.
            valorVisual = PREF_pantalla_apagar;
        } else {   // Caso: Hay un número.
            valorVisual = PREF_pantalla_apagar + " " + getString(R.string.val_min);
        }
        txt = getString(R.string.lb_pantalla_apagar, valorVisual);
        return txt;
    }

    @NonNull
    private String getPref_pantalla_tamanoFuente() { // SeekBarPreference (Devuelve int)
        String txt;
        int PREF_pantalla_tamanoFuente = pref.getInt(getString(R.string.key_pantalla_tamanioFuente),
                getResources().getInteger(R.integer.def_pantalla_tamanioFuente));
        preferencesLog(getString(R.string.key_pantalla_tamanioFuente), PREF_pantalla_tamanoFuente);
        txt = getString(R.string.lb_pantalla_tamanoFuente, PREF_pantalla_tamanoFuente);
        return txt;
    }

    @NonNull
    private String getPref_pantalla_modoObscuro() { // SwitchPreference (Devuelve boolean)
        String txt;
        boolean PREF_pantalla_modoOscuro = pref.getBoolean(getString(R.string.key_pantalla_modoObscuro), false);
        preferencesLog(getString(R.string.key_pantalla_modoObscuro), PREF_pantalla_modoOscuro);
        txt = getString(R.string.lb_pantalla_modoObscuro, (PREF_pantalla_modoOscuro ? getString(R.string.val_ON) : getString(R.string.val_OFF)));
        return txt;
    }

    @NonNull
    private String getPref_conn_limitarDatos() { // ListPreference (Devuelve String)
        String txt;
        String valorTecnico = pref.getString(getString(R.string.key_conn_limitarDatos), getString(R.string.val_NULL));
        preferencesLog(getString(R.string.key_conn_limitarDatos), valorTecnico);
        String PREF_conn_limitarDatos;
        switch (valorTecnico) {
            case "1" -> PREF_conn_limitarDatos = getString(R.string.val1_conn_limitarDatos);
            case "2" -> PREF_conn_limitarDatos = getString(R.string.val2_conn_limitarDatos);
            case "3" -> PREF_conn_limitarDatos = getString(R.string.val3_conn_limitarDatos);
            default ->  PREF_conn_limitarDatos = getString(R.string.val_NULL);
        }
        txt = getString(R.string.lb_conn_limitarDatos, PREF_conn_limitarDatos);
        return txt;
    }

    @NonNull
    private String getPref_conn_calidadDescargas() {  // MultiSelectListPreference (Devuelve un Set<String>)
        String txt;
        // Recuperamos el Set de valores técnicos (ej: ["1", "480"])
        java.util.Set<String> calidadVideo = pref.getStringSet(getString(R.string.key_conn_descargarVideo), new java.util.HashSet<>());
        preferencesLog(getString(R.string.key_conn_descargarVideo), calidadVideo);
        String PREF_conn_descargarVideo;
        if (calidadVideo != null && !calidadVideo.isEmpty()) {
            // Lista temporal para los textos que vamos a mostrar
            java.util.List<String> textosSeleccionados = new java.util.ArrayList<>();
            for (String valor : calidadVideo) {
                switch (valor) {
                    case "480"  -> textosSeleccionados.add("480p");
                    case "1080" -> textosSeleccionados.add("1080p");
                    case "1"    -> textosSeleccionados.add("HD");
                    case "0"    -> textosSeleccionados.add(getString(R.string.original));
                    default     -> textosSeleccionados.add(valor);
                }
            }
            // Unimos los textos con comas
            PREF_conn_descargarVideo = String.join(", ", textosSeleccionados);
        } else { // Caso: No hay nada seleccionado (o borrado)
            PREF_conn_descargarVideo = getString(R.string.val_NULL);
        }
        txt = getString(R.string.lb_conn_calidadDescargas, PREF_conn_descargarVideo);
        return txt;
    }

    @NonNull
    private String getPref_conn_modoRestrictivo() { // SwitchPreferences (Devuelve boolean)
        String txt;
        boolean PREF_conn_modoRestrictivo = pref.getBoolean(getString(R.string.key_conn_modoRestrictivo), false);
        preferencesLog(getString(R.string.key_conn_modoRestrictivo), PREF_conn_modoRestrictivo);
        txt = getString(R.string.lb_conn_modoRestrictivo,
                (PREF_conn_modoRestrictivo ? getString(R.string.val_SI) : getString(R.string.val_NO)));
        return txt;
    }

    @NonNull
    private String getPref_conn_estadisticasUso() { // SwitchPreferences (Devuelve boolean)
        String txt;
        boolean PREF_conn_estadisticasUSO = pref.getBoolean(getString(R.string.key_conn_estadisticasUso), false);
        preferencesLog(getString(R.string.key_conn_estadisticasUso), PREF_conn_estadisticasUSO);
        txt = getString(R.string.lb_conn_estadisticasUso, (PREF_conn_estadisticasUSO ? getString(R.string.val_ON) : getString(R.string.val_OFF)));
        return txt;
    }

}