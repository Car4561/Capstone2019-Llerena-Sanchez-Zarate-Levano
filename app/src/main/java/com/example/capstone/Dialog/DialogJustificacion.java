package com.example.capstone.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.capstone.Model.Justificacion;

public class DialogJustificacion extends DialogFragment {

    Justificacion justificacion;
    public DialogJustificacion( Justificacion justificacion){
        this.justificacion = justificacion;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Revision de Justificación");
        return super.onCreateDialog(savedInstanceState);
    }
}
