package dam.pmdm.settings;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {

    private SettingsController SetCtrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        setSupportActionBar(findViewById(R.id.myToolBar));

        SetCtrl = new SettingsController(this);
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
        preferencesLog();
    }

    private void preferencesLog() {
        String tag = "SETTINGS_PREFERENCES";
        Log.d(tag, "name=relax_descansar       value=" + SetCtrl.getVal_relax_descansar());
        Log.d(tag, "name=relax_nombreAlarma    value=" + SetCtrl.getVal_relax_nombreAlarma());
        Log.d(tag, "name=pantalla_apagar       value=" + SetCtrl.getVal_pantalla_apagar());
        Log.d(tag, "name=pantalla_tamanoFuente value=" + SetCtrl.getVal_pantalla_tamanoFuente());
        Log.d(tag, "name=pantalla_modoObscuro  value=" + SetCtrl.getVal_pantalla_modoObscuro());
        Log.d(tag, "name=conn_limitarDatos     value=" + SetCtrl.getVal_conn_limitarDatos());
        Log.d(tag, "name=conn_calidadDescargas value=" + SetCtrl.getVal_conn_calidadDescargas());
        Log.d(tag, "name=conn_modoRestrictivo  value=" + SetCtrl.getVal_conn_modoRestrictivo());
        Log.d(tag, "name=conn_estadisticasUso  value=" + SetCtrl.getVal_conn_estadisticasUso());
    }

    private void preferencesShow() {
        // --- SECCIÓN: RELAX ---
        ((TextView) findViewById(R.id.lb_relax_descansar)).setText(SetCtrl.getStr_relax_descansar());
        ((TextView) findViewById(R.id.lb_relax_nombreAlarma)).setText(SetCtrl.getStr_relax_nombreAlarma());

        // --- SECCIÓN: PANTALLA ---
        ((TextView) findViewById(R.id.lb_pantalla_apagar)).setText(SetCtrl.getStr_pantalla_apagar());
        ((TextView) findViewById(R.id.lb_pantalla_tamanoFuente)).setText(SetCtrl.getStr_pantalla_tamanoFuente());
        ((TextView) findViewById(R.id.lb_pantalla_modoObscuro)).setText(SetCtrl.getStr_pantalla_modoObscuro());

        // --- SECCIÓN: CONEXIÓN ---
        ((TextView) findViewById(R.id.lb_conn_limitarDatos)).setText(SetCtrl.getStr_conn_limitarDatos());
        ((TextView) findViewById(R.id.lb_conn_calidadDescargas)).setText(SetCtrl.getStr_conn_calidadDescargas());
        ((TextView) findViewById(R.id.lb_conn_modoRestrictivo)).setText(SetCtrl.getStr_conn_modoRestrictivo());
        ((TextView) findViewById(R.id.lb_conn_estadisticasUso)).setText(SetCtrl.getStr_conn_estadisticasUso());
    }

    private void confirmarBorrado() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.tit_confimDelete)
                .setMessage(R.string.msg_confimDelete)
                .setIcon(R.drawable.ic_warning)
                .setPositiveButton(R.string.pos_confimDelete, (dialog, which) -> {
                    SetCtrl.preferencesDelete();
                    preferencesShow();
                    preferencesLog();
                    Toast.makeText(this, R.string.tst_confimDelete, Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton(R.string.neg_confimDelete, null)
                .show();
    }

    private void preferencesOpen() {
       Intent i = new Intent(this, SettingsActivity.class);
       startActivity(i);
    }

}