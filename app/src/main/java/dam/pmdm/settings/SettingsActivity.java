package dam.pmdm.settings;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Establecemos el layout que contiene el FrameLayout (el contenedor)
        setContentView(R.layout.activity_settings);

        // 2. Cargamos el Fragmento de Preferencias
        // Usamos el check de 'savedInstanceState == null' para que no se duplique
        // el fragmento si el alumno gira la pantalla del móvil.
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings_container, new SettingsFragment())
                    .commit();
        }
    }
}