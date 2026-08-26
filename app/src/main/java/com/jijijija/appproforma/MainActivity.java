package com.jijijija.appproforma;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText etCodigo, etProducto, etPrecio, etCantidad;
    private Button btnNuevo, btnGrabar, btnActualizar, btnEliminar;
    private TextView tvTotal;
    private RecyclerView rvRegistros;
    private TextView tvTotal;
    private Button btnNuevo, btnGrabar, btnActualizar, btnEliminar;

    private final ArrayList<ProformaItem> lista = new ArrayList<>();
    private ProformaAdapter adaptador;
    private int posicionSeleccionada = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enlazarVistas();
        configurarLista();
        configurarBotones();
        actualizarTotalAcumulado();
    }

    private void enlazarVistas() {
        // Inicio Programacion
        etCodigo = findViewById(R.id.etCodigo);
        etProducto = findViewById(R.id.etProducto);
        etPrecio = findViewById(R.id.etPrecio);
        etCantidad = findViewById(R.id.etCantidad);

        tvTotal = findViewById(R.id.tvTotal);

        tvTotal = findViewById(R.id.tvTotal);
        btnNuevo = findViewById(R.id.btnNuevo);
        btnGrabar = findViewById(R.id.btnGrabar);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnEliminar = findViewById(R.id.btnEliminar);

        rvRegistros = findViewById(R.id.rvRegistros);
    }

    private void configurarLista() {

        adaptador = new ProformaAdapter(lista, position -> {

            posicionSeleccionada = position;

            ProformaItem item = lista.get(position);

            etCodigo.setText(item.getCodigo());
            etProducto.setText(item.getProducto());

            etPrecio.setText(
                    String.format(Locale.US, "%.2f", item.getPrecio())
            );

            etCantidad.setText(
                    String.valueOf(item.getCantidad())
            );
        });

        rvRegistros.setLayoutManager(
                new LinearLayoutManager(this)
        );

        rvRegistros.setAdapter(adaptador);
    }

    private void configurarBotones() {

        // BOTÓN NUEVO
        btnNuevo.setOnClickListener(v -> {
            limpiarCampos();
        });
        androidx.recyclerview.widget.RecyclerView rvRegistros = findViewById(R.id.rvRegistros);
        rvRegistros.setLayoutManager(new LinearLayoutManager(this));
        adaptador = new ProformaAdapter(lista, this::seleccionarItem);
        rvRegistros.setAdapter(adaptador);

        // Nuevo
        btnNuevo.setOnClickListener(v -> limpiarCampos());


        // BOTÓN GRABAR
        btnGrabar.setOnClickListener(v -> {

            ProformaItem nuevoItem = leerFormulario();

            if (nuevoItem == null) {
                return;
            }

            lista.add(nuevoItem);

            adaptador.notifyItemInserted(
                    lista.size() - 1
            );

            actualizarTotalAcumulado();

            if (!validarCampos()) return;

            String codigo = etCodigo.getText().toString().trim();
            String producto = etProducto.getText().toString().trim();
            double precio = Double.parseDouble(etPrecio.getText().toString().trim());
            int cantidad = Integer.parseInt(etCantidad.getText().toString().trim());

            lista.add(new ProformaItem(codigo, producto, precio, cantidad));
            adaptador.notifyItemInserted(lista.size() - 1);
            actualizarTotal();
            limpiarCampos();

            Toast.makeText(
                    this,
                    "Producto agregado",
                    Toast.LENGTH_SHORT
            ).show();
        });


        // BOTÓN ACTUALIZAR
        btnActualizar.setOnClickListener(v -> {

            if (posicionSeleccionada < 0 ||
                    posicionSeleccionada >= lista.size()) {

                Toast.makeText(
                        this,
                        "Selecciona un producto de la lista",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            ProformaItem datosActualizados = leerFormulario();

            if (datosActualizados == null) {
                return;
            }

            ProformaItem item =
                    lista.get(posicionSeleccionada);

            item.setCodigo(
                    datosActualizados.getCodigo()
            );

            item.setProducto(
                    datosActualizados.getProducto()
            );

            item.setPrecio(
                    datosActualizados.getPrecio()
            );

            item.setCantidad(
                    datosActualizados.getCantidad()
            );

            adaptador.notifyItemChanged(
                    posicionSeleccionada
            );

            actualizarTotalAcumulado();

            limpiarCampos();

            Toast.makeText(
                    this,
                    "Producto actualizado",
                    Toast.LENGTH_SHORT
            ).show();
        // Actualizar
        btnActualizar.setOnClickListener(v -> {
            if (posicionSeleccionada == -1) {
                Toast.makeText(this, "Selecciona un producto de la lista", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!validarCampos()) return;

            ProformaItem item = lista.get(posicionSeleccionada);
            item.setCodigo(etCodigo.getText().toString().trim());
            item.setProducto(etProducto.getText().toString().trim());
            item.setPrecio(Double.parseDouble(etPrecio.getText().toString().trim()));
            item.setCantidad(Integer.parseInt(etCantidad.getText().toString().trim()));

            adaptador.notifyItemChanged(posicionSeleccionada);
            actualizarTotal();
            limpiarCampos();
        });


        // BOTÓN ELIMINAR
        btnEliminar.setOnClickListener(v -> {

            if (posicionSeleccionada < 0 ||
                    posicionSeleccionada >= lista.size()) {

                Toast.makeText(
                        this,
                        "Selecciona un producto de la lista",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }
            if (posicionSeleccionada == -1) {
                Toast.makeText(this, "Selecciona un producto de la lista", Toast.LENGTH_SHORT).show();
                return;
            }
            lista.remove(posicionSeleccionada);
            adaptador.notifyDataSetChanged();
            actualizarTotal();
            limpiarCampos();
        });
        // Fin Programacion

            int posicionEliminada =
                    posicionSeleccionada;

            lista.remove(posicionEliminada);

            adaptador.notifyItemRemoved(
                    posicionEliminada
            );

            actualizarTotalAcumulado();

            limpiarCampos();

            Toast.makeText(
                    this,
                    "Producto eliminado",
                    Toast.LENGTH_SHORT
            ).show();
        });
    }


    private ProformaItem leerFormulario() {

        String codigo =
                obtenerTexto(etCodigo);

        String producto =
                obtenerTexto(etProducto);

        String precioTexto =
                obtenerTexto(etPrecio);

        String cantidadTexto =
                obtenerTexto(etCantidad);


        // VALIDAR CÓDIGO VACÍO
        if (TextUtils.isEmpty(codigo)) {

            etCodigo.setError(
                    "Ingresa el código"
            );

            etCodigo.requestFocus();

            return null;
        }


        // CÓDIGO DEBE TENER EXACTAMENTE 6 CARACTERES
        if (codigo.length() != 6) {

            etCodigo.setError(
                    "El código debe tener exactamente 6 caracteres"
            );

            etCodigo.requestFocus();

            return null;
        }


        // VALIDAR PRODUCTO VACÍO
        if (TextUtils.isEmpty(producto)) {

            etProducto.setError(
                    "Ingresa el producto"
            );

            etProducto.requestFocus();

            return null;
        }


        // PRODUCTO MÁXIMO 40 CARACTERES
        if (producto.length() > 40) {

            etProducto.setError(
                    "El producto debe tener máximo 40 caracteres"
            );

            etProducto.requestFocus();

            return null;
        }


        // VALIDAR PRECIO VACÍO
        if (TextUtils.isEmpty(precioTexto)) {

            etPrecio.setError(
                    "Ingresa el precio"
            );

            etPrecio.requestFocus();

            return null;
        }


        // VALIDAR CANTIDAD VACÍA
        if (TextUtils.isEmpty(cantidadTexto)) {

            etCantidad.setError(
                    "Ingresa la cantidad"
            );

            etCantidad.requestFocus();

            return null;
        }


        try {

            double precio =
                    Double.parseDouble(
                            precioTexto.replace(',', '.')
                    );

            int cantidad =
                    Integer.parseInt(
                            cantidadTexto
                    );


            // PRECIO NO PUEDE SER NEGATIVO
            if (precio < 0) {

                etPrecio.setError(
                        "El precio no puede ser negativo"
                );

                etPrecio.requestFocus();

                return null;
            }


            // CANTIDAD MAYOR QUE CERO
            if (cantidad <= 0) {

                etCantidad.setError(
                        "La cantidad debe ser mayor que 0"
                );

                etCantidad.requestFocus();

                return null;
            }


            return new ProformaItem(
                    codigo,
                    producto,
                    precio,
                    cantidad
            );

        } catch (NumberFormatException e) {

            Toast.makeText(
                    this,
                    "Revisa el precio y la cantidad",
                    Toast.LENGTH_SHORT
            ).show();

            return null;
        }
    }


    private String obtenerTexto(
            TextInputEditText campo
    ) {

        if (campo.getText() == null) {
            return "";
        }

        return campo.getText()
                .toString()
                .trim();
    }


    private void actualizarTotalAcumulado() {

        double total = 0.0;

        for (ProformaItem item : lista) {

            total += item.getTotal();
        }

        tvTotal.setText(
                String.format(
                        Locale.US,
                        "Total: S/. %.2f",
                        total
                )
        );
    }


    private void limpiarCampos() {

    // Se llama al tocar un ítem de la lista (RecyclerView)
    private void seleccionarItem(int position) {
        posicionSeleccionada = position;
        ProformaItem item = lista.get(position);
        etCodigo.setText(item.getCodigo());
        etProducto.setText(item.getProducto());
        etPrecio.setText(String.valueOf(item.getPrecio()));
        etCantidad.setText(String.valueOf(item.getCantidad()));
    }

    private boolean validarCampos() {
        if (etCodigo.getText().toString().trim().isEmpty()
                || etProducto.getText().toString().trim().isEmpty()
                || etPrecio.getText().toString().trim().isEmpty()
                || etCantidad.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            Double.parseDouble(etPrecio.getText().toString().trim());
            Integer.parseInt(etCantidad.getText().toString().trim());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Precio o cantidad inválidos", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void actualizarTotal() {
        double total = 0;
        for (ProformaItem item : lista) {
            total += item.getTotal();
        }
        tvTotal.setText(String.format(Locale.getDefault(), "Total: S/. %.2f", total));
    }

    // Inicio Implementar
    private void limpiarCampos() {
        etCodigo.setText("");
        etProducto.setText("");
        etPrecio.setText("");
        etCantidad.setText("");

        posicionSeleccionada = -1;

        posicionSeleccionada = -1;
        etCodigo.requestFocus();
    }
}