package com.am2.eshopapp.ui.home;

import static androidx.navigation.Navigation.findNavController;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.am2.eshopapp.R;
import com.am2.eshopapp.databinding.FragmentProductCreateBinding;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ProductCreateFragment extends Fragment {

    FirebaseStorage storage = FirebaseStorage.getInstance();
    private FragmentProductCreateBinding binding;
    private FirebaseFirestore db;
    private Uri imageUri,downloadUrl;
    ProgressDialog progressDialog;
    StorageReference storageReference;

    public void pickImage(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        galleryActivityLauncher.launch(intent);
    }

    private ActivityResultLauncher<Intent> galleryActivityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode()== Activity.RESULT_OK){
                        Intent data = result.getData(); // obtiene la data cuando selecciona la imagen
                        Uri uri = data.getData(); // obtiene la ruta de la imagen
                        if(uri != null){
                            imageUri = uri;
                            //addProductBinding.ivProduct.setImageURI(uri);
                            binding.ivProduct.setImageURI(uri);
                        }
                    }
                    else{
                        Toast.makeText(
                                getContext(),
                                "No image selected",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }
            }
    );

    public void uploadImage(){

    }

    public ProductCreateFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentProductCreateBinding.inflate(inflater, container, false);
        Button jbtnCancelProductCreate = binding.btnCancelProductCreate;
        Button jbtnProductCreate = binding.btnProductCreate;
        Button jbtnPickImg = binding.btnPickImage;
        EditText jetProductName = binding.etProductName;
        EditText jetProductPrice = binding.etProductPrice;
        EditText jetProductDescription = binding.etProductDescription;
        EditText jetProductStock = binding.etProductStock;
        db= FirebaseFirestore.getInstance();

        jbtnCancelProductCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findNavController(view).navigate(R.id.nav_home);
            }
        });

        jbtnPickImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });

        jbtnProductCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name, description, stock, price;
                name = jetProductName.getText().toString();
                description = jetProductDescription.getText().toString();
                stock = jetProductStock.getText().toString();
                price = jetProductPrice.getText().toString();

                // Validar si hay campos vacios
                if (
                        !name.isEmpty() &&
                                !description.isEmpty() &&
                                !stock.isEmpty() &&
                                !price.isEmpty()
                ){
                    //saveProductToFirestore();
                    //uploadImage();

                    progressDialog = new ProgressDialog(getContext());
                    progressDialog.setTitle("Agregando producto");
                    progressDialog.show();
                    SimpleDateFormat dateFormatter = new SimpleDateFormat(
                            "yyyy_MM_dd_HH_mm_ss",
                            Locale.US
                    );
                    Date dateNow = new Date();
                    String filenameImage = dateFormatter.format(dateNow);
                    storageReference = FirebaseStorage.getInstance().getReference(
                            "products/"+filenameImage);
                    UploadTask uploadTask = storageReference.putFile(imageUri);
                    Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if(!task.isSuccessful()){
                                throw task.getException();
                            }
                            return storageReference.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if(task.isSuccessful()){
                                downloadUrl = task.getResult(); // obtiene la url de descarga
                                //addProduct();
                                saveProductToFirestore();
                                findNavController(view).navigate(R.id.nav_home);
                            }
                        }
                    });



                }else {
                    Toast.makeText(getContext(), "Llene todos los campos.", Toast.LENGTH_LONG).show();
                }

            }
        });
        return binding.getRoot();
    }

    public void saveProductToFirestore(){
        EditText jetProductName = binding.etProductName;
        EditText jetProductPrice = binding.etProductPrice;
        EditText jetProductDescription = binding.etProductDescription;
        EditText jetProductStock = binding.etProductStock;

        Map<String, Object> product = new HashMap<>();
        String productName = jetProductName.getText().toString();
        String productPrice = jetProductPrice.getText().toString();
        String productDescription = jetProductDescription.getText().toString();
        String productStock = jetProductStock.getText().toString();

        product.put("name", productName);
        product.put("price", productPrice);
        product.put("description", productDescription);
        product.put("stock", productStock);
        product.put("imageUrl",downloadUrl.toString());
        db.collection("products")
                .add(product)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(),
                                "Operation complete!!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(),
                                "Upps, product create fail...", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}