package com.hdcompany.admin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hdcompany.admin.R;
import com.hdcompany.admin.databinding.ActivityAddNewSportClothesBinding;
import com.hdcompany.admin.firebase.Auth;
import com.hdcompany.admin.model.Image;
import com.hdcompany.admin.model.SportClothes;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class AddNewSportClothesActivity extends AppCompatActivity {


    private boolean isPop = false;
    private boolean checkBanner = false;
    private boolean checkSecondBanner = false;
    private boolean checkListImage = false;
    private ActivityAddNewSportClothesBinding binding;
    private List<String> LIST_IMAGE = new ArrayList<>();
    private boolean action;
    private String keySport;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNewSportClothesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        action = getIntent().getBooleanExtra("action", false);

        if (!action) {
            binding.textViewTitle.setText("Update Sport Cloth");
            binding.commit.setText("UPDATE");
            keySport = getIntent().getStringExtra("keySport");
            Auth.sportClothsDatabase(this).getReference("sports").child(keySport)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                SportClothes sportClothes = snapshot.getValue(SportClothes.class);
                                if (sportClothes != null) {
                                    binding.setSportCloths(sportClothes);
                                    binding.editTextName.setText(sportClothes.getName());
                                    binding.editTextPrice.setText(String.valueOf(sportClothes.getPrice()));
                                    binding.editTextCount.setText(String.valueOf(sportClothes.getCount()));
                                    binding.editTextDescription.setText(sportClothes.getDescription());
                                    binding.editTextSale.setText(String.valueOf(sportClothes.getSale()));
                                    binding.editTextTotalPrice.setText(String.valueOf(sportClothes.getTotalPrice()));
                                    if (sportClothes.isPopular()) {
                                        binding.popular.setChecked(true);
                                    } else {
                                        binding.notPopular.setChecked(true);
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
        }
        /*Same

         */
        binding.isPopularRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Get the checked RadioButton
                RadioButton checkedRadioButton = findViewById(checkedId);
                if (checkedRadioButton != null) {
                    // Get the text of the checked RadioButton
                    String radioButtonValue = checkedRadioButton.getText().toString();
                    if (radioButtonValue.equals("Popular")) {
                        isPop = true;
                    } else {
                        isPop = false;
                    }
                    // Do something with the selected value
                    // For example, show a toast
                    System.out.println("Selected value: " + radioButtonValue);
                }
            }
        });

        /*
        Same
         */
        binding.editTextPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                try {
                    String S = s.toString();
                    if (S != null || !S.isEmpty()) {
                        System.out.println(S);
                        int price = Integer.parseInt(S);
                        String sale = null;
                        if (binding.editTextSale.getText().toString().trim() != null && binding.editTextSale.getText().toString().trim().equals("")) {
                            sale = "0";
                        } else {
                            sale = binding.editTextSale.getText().toString().trim();
                        }
                        int saleInt = Integer.parseInt(sale);
                        int Total = price - (price * saleInt / 100);
                        binding.editTextTotalPrice.setText(String.valueOf(Total));
                    } else {
                        binding.editTextTotalPrice.setText(0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    String S = s.toString();
                    if (S != null || !S.isEmpty()) {
                        System.out.println(S);
                        int price = Integer.parseInt(S);
                        String sale = null;
                        if (binding.editTextSale.getText().toString().trim() != null && binding.editTextSale.getText().toString().trim().equals("")) {
                            sale = "0";
                        } else {
                            sale = binding.editTextSale.getText().toString().trim();
                        }
                        int saleInt = Integer.parseInt(sale);
                        int Total = price - (price * saleInt / 100);
                        binding.editTextTotalPrice.setText(String.valueOf(Total));
                    } else {
                        binding.editTextTotalPrice.setText(0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    String S = s.toString();
                    if (S != null || !S.isEmpty()) {
                        System.out.println(S);
                        int price = Integer.parseInt(S);
                        String sale = null;
                        if (binding.editTextSale.getText().toString().trim() != null && binding.editTextSale.getText().toString().trim().equals("")) {
                            sale = "0";
                        } else {
                            sale = binding.editTextSale.getText().toString().trim();
                        }
                        int saleInt = Integer.parseInt(sale);
                        int Total = price - (price * saleInt / 100);
                        binding.editTextTotalPrice.setText(String.valueOf(Total));
                    } else {
                        binding.editTextTotalPrice.setText(0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        /*Same

         */
        binding.editTextSale.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                try {
                    String S = s.toString();
                    if (S != null || !S.isEmpty()) {
                        System.out.println(S);
                        int sale = Integer.parseInt(S);
                        String price = null;
                        if (binding.editTextPrice.getText().toString().trim() != null && binding.editTextPrice.getText().toString().trim().equals("")) {
                            price = "0";
                        } else {
                            price = binding.editTextPrice.getText().toString().trim();
                        }
                        int priceInt = Integer.parseInt(price);
                        int Total = priceInt - (priceInt * sale / 100);
                        binding.editTextTotalPrice.setText(String.valueOf(Total));
                    } else {
                        binding.editTextTotalPrice.setText(0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    String S = s.toString();
                    if (S != null || !S.isEmpty()) {
                        System.out.println(S);
                        int sale = Integer.parseInt(S);
                        String price = null;
                        if (binding.editTextPrice.getText().toString().trim() != null && binding.editTextPrice.getText().toString().trim().equals("")) {
                            price = "0";
                        } else {
                            price = binding.editTextPrice.getText().toString().trim();
                        }
                        int priceInt = Integer.parseInt(price);
                        int Total = priceInt - (priceInt * sale / 100);
                        binding.editTextTotalPrice.setText(String.valueOf(Total));
                    } else {
                        binding.editTextTotalPrice.setText(0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    String S = s.toString();
                    if (S != null || !S.isEmpty()) {
                        System.out.println(S);
                        int sale = Integer.parseInt(S);
                        String price = null;
                        if (binding.editTextPrice.getText().toString().trim() != null && binding.editTextPrice.getText().toString().trim().equals("")) {
                            price = "0";
                        } else {
                            price = binding.editTextPrice.getText().toString().trim();
                        }
                        int priceInt = Integer.parseInt(price);
                        int Total = priceInt - (priceInt * sale / 100);
                        binding.editTextTotalPrice.setText(String.valueOf(Total));
                    } else {
                        binding.editTextTotalPrice.setText(0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        binding.commit.setOnClickListener(v -> {
            if (action) { // action true the adding new
                // The add new Sports Cloths
                if (checkBanner) {
                    if (checkSecondBanner) {
                        if (checkListImage) {
                            processUploadedImages();
                            System.out.println("LIST_IMAGE" + LIST_IMAGE.toString());
                            System.out.println("LIST_IMAGE_IMAGE" + LIST_IMAGE_IMAGE.toString());
                            pushSportClothesToDatabase(verifyDataSports());
                        } else {
                            Toast.makeText(this, "Cannot leave images empty", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Cannot leave second banner empty", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Cannot leave banner empty", Toast.LENGTH_SHORT).show();
                }
            } else {
                // The Update one
                if (checkBanner) {
                    binding.getSportCloths().setBanner(LIST_IMAGE.get(0));
                    LIST_IMAGE.remove(0);
                }
                if (checkSecondBanner) {
                    binding.getSportCloths().setImage(LIST_IMAGE.get(0));
                    LIST_IMAGE.remove(0);
                }
                if (checkListImage) {
                    processUploadedImages();
                    binding.getSportCloths().setImages(LIST_IMAGE_IMAGE);
                }
                try {
                    updateSportClothesFromDatabase(verifyDataSports());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }

            }
            this.finish();
        });

        /*
        Same
         */
        binding.chooseBannerImageButton.setOnClickListener(v -> {
            openGalleryForBanner();
        });
        /*
        Same
         */
        binding.chooseSecondBannerImageButton.setOnClickListener(v -> {
            openGalleryForSecondBanner();
        });
        /*same

         */
        binding.chooseListOfImagesButton.setOnClickListener(v -> {
            openGalleryForImages();
        });
    }

    private SportClothes verifyDataSports() {
        SportClothes sp = getDataFromView();
        if (sp.getName() != null && !sp.getName().equals("")) {
            if (sp.getDescription() != null && !sp.getDescription().equals("")) {
                if (sp.getPrice() > 0 && sp.getPrice() < 2000000000) {
                    if (sp.getSale() > -1 && sp.getSale() < 100) {
                        if (sp.getCount() > -1 && sp.getCount() < 1000000000) {
                            Toast.makeText(this, "Updating", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Count out of range", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Sale cannot larger than 100%", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Price out of range", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Description cannot be null", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Name cannot be null", Toast.LENGTH_SHORT).show();
        }
        if(action){
            return sp;
        }

        binding.getSportCloths().setId(keySport);
        binding.getSportCloths().setName(sp.getName());
        binding.getSportCloths().setDescription(sp.getDescription());
        binding.getSportCloths().setCount(sp.getCount());
        binding.getSportCloths().setPrice(sp.getPrice());
        binding.getSportCloths().setTotalPrice(sp.getTotalPrice());
        binding.getSportCloths().setSale(sp.getSale());
        binding.getSportCloths().setPopular(sp.isPopular());
        return binding.getSportCloths();
    }

    private void updateSportClothesFromDatabase(SportClothes sportClothes) throws IllegalAccessException {
        HashMap<String,Object> updates = new HashMap<>();
        Field[] fields = SportClothes.class.getDeclaredFields();
        for(Field field : fields){
            field.setAccessible(true);
            Object o = field.get(sportClothes);
            updates.put(field.getName(), o);
        }
        System.out.println("MAP: " + updates.toString() + "END MAP");
//        Auth.sportClothsDatabase(this).getReference("sports").child(keySport)
//                .updateChildren(updates)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        Toast.makeText(AddNewSportClothesActivity.this, "Sport clothes updated successfully", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(AddNewSportClothesActivity.this, "Failed to update sport clothes: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
    }

    /*Same

     */
    private void pushSportClothesToDatabase(SportClothes sportClothes) {
        // Generate a unique key for the new SportClothes entry
        String key = Auth.sportClothsDatabase(this).getReference("sports").push().getKey();

        // Set the SportClothes object under the unique key
        sportClothes.setId(key);
        Auth.sportClothsDatabase(this).getReference("sports").child(key).setValue(sportClothes)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Data successfully saved
                        Toast.makeText(AddNewSportClothesActivity.this, "SportClothes added successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Failed to save data
                        Toast.makeText(AddNewSportClothesActivity.this, "Failed to add SportClothes", Toast.LENGTH_SHORT).show();
                        Log.e("TAG", "Error adding SportClothes", e);
                    }
                });
    }


    /*
    The following below are same
    same

     */
    private void uploadImageToFirebaseStorage(Uri imageUri) {
        StorageReference storageRef = Auth.sportClothesFireStorage(this).getReference("images_sport_clothes");
        String imageName = "image_" + System.currentTimeMillis(); // Unique name for the image
        StorageReference imageRef = storageRef.child(imageName);
        imageRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        LIST_IMAGE.add(uri.toString());
                        Log.d("ImageDownloadUrl", uri.toString());
                        Log.d("TAGBECHJ", uri.toString());
                    }
                });
            }
        });

    }

    private List<Image> LIST_IMAGE_IMAGE = new ArrayList<>();

    private void processUploadedImages() {
        // All images uploaded, you can now use the download URLs stored in imageUrls list
        for (String imageUrl : LIST_IMAGE) {
            // Do something with the download URL (e.g., store it in Firestore or display it to the user)
            System.out.println("Uploaded image URL: " + imageUrl);
            LIST_IMAGE_IMAGE.add(new Image(LIST_IMAGE.indexOf(imageUrl), imageUrl));
        }
    }

    private SportClothes getDataFromView() {
        SportClothes sportClothes = new SportClothes();
        try {
            String name = binding.editTextName.getText().toString().trim();
            String description = binding.editTextDescription.getText().toString().trim();
            int price = Integer.parseInt(binding.editTextPrice.getText().toString().trim());
            int sale = Integer.parseInt(binding.editTextSale.getText().toString().trim());
            int count = Integer.parseInt(binding.editTextCount.getText().toString().trim());
            int totalPrice = Integer.parseInt(binding.editTextTotalPrice.getText().toString().trim());
            boolean popular = isPop;
            String image = null;
            String banner = null;
            List<Image> images = null;
            try {
                image = LIST_IMAGE.get(0);
                LIST_IMAGE.remove(0);
                LIST_IMAGE_IMAGE.remove(0);
                banner = LIST_IMAGE.get(0);
                LIST_IMAGE.remove(0);
                LIST_IMAGE_IMAGE.remove(0);
                images = LIST_IMAGE_IMAGE;
            } catch (Exception e) {
                e.printStackTrace();
            }

            sportClothes = new SportClothes("id", name, image, banner, description, price, sale, count, totalPrice, popular, images);
        } catch (Exception e) {
            System.out.println("ERROR AT GET DATA FROM VIEW");
            e.printStackTrace();
        }
        System.out.println("sportClothes:406:AddNewSportClothesActivity.java: " + sportClothes.toString());
        binding.setSportCloths(sportClothes);
        return sportClothes;
    }


    /*
    Pick Image
     */
    private static final int PICK_BANNER_REQUEST = 1;
    private Uri bannerUri;
    private static final int PICK_SECOND_BANNER_REQUEST = 2;
    private Uri secondBannerUri;
    private static final int PICK_IMAGE_LIST_REQUEST = 3;

    private void openGalleryForBanner() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Banner"), PICK_BANNER_REQUEST);
    }

    private void openGalleryForSecondBanner() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Second Banner"), PICK_SECOND_BANNER_REQUEST);
    }

    private void openGalleryForImages() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Please Select Multiple Images"), PICK_IMAGE_LIST_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_BANNER_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            bannerUri = selectedImageUri;
            uploadImageToFirebaseStorage(bannerUri);
            // Do something with the selected image URI
            // For example, display it in an ImageView
            binding.checkStatusBannerImage.setImageResource(R.drawable.v_sign);
            binding.chooseBannerImageButton.setClickable(false);
            checkBanner = true;
        }
        if (requestCode == PICK_SECOND_BANNER_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            secondBannerUri = selectedImageUri;
            uploadImageToFirebaseStorage(secondBannerUri);
            // Do something with the selected image URI
            // For example, display it in an ImageView
            binding.checkStatusSecondBannerImage.setImageResource(R.drawable.v_sign);
            binding.chooseSecondBannerImageButton.setClickable(false);
            checkSecondBanner = true;
        }
        if (requestCode == PICK_IMAGE_LIST_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            ClipData clipData = data.getClipData();
            if (clipData != null) {
                // Multiple images selected
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    Uri imageUri = clipData.getItemAt(i).getUri();
                    uploadImageToFirebaseStorage(imageUri);
                    // Process each selected image URI
//                    uploadImageToFirebaseStorage(imageUri);
                }
            } else {
                // Single image selected
                Uri imageUri = data.getData();
                uploadImageToFirebaseStorage(imageUri);
                // Process the selected image URI
//                uploadImageToFirebaseStorage(imageUri);
            }
            checkListImage = true;
            binding.checkStatusListImages.setImageResource(R.drawable.v_sign);
        }
    }
}