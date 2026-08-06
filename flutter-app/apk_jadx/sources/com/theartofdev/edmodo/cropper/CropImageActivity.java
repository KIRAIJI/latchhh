package com.theartofdev.edmodo.cropper;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.hjq.permissions.Permission;
import com.luck.picture.lib.config.PictureMimeType;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import java.io.File;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class CropImageActivity extends AppCompatActivity implements CropImageView.OnSetImageUriCompleteListener, CropImageView.OnCropImageCompleteListener {
    private Uri mCropImageUri;
    private CropImageView mCropImageView;
    private CropImageOptions mOptions;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.crop_image_activity);
        this.mCropImageView = (CropImageView) findViewById(R.id.cropImageView);
        Bundle bundleExtra = getIntent().getBundleExtra(CropImage.CROP_IMAGE_EXTRA_BUNDLE);
        this.mCropImageUri = (Uri) bundleExtra.getParcelable(CropImage.CROP_IMAGE_EXTRA_SOURCE);
        this.mOptions = (CropImageOptions) bundleExtra.getParcelable(CropImage.CROP_IMAGE_EXTRA_OPTIONS);
        if (bundle == null) {
            Uri uri = this.mCropImageUri;
            if (uri == null || uri.equals(Uri.EMPTY)) {
                if (CropImage.isExplicitCameraPermissionRequired(this)) {
                    requestPermissions(new String[]{Permission.CAMERA}, CropImage.CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE);
                } else {
                    CropImage.startPickImageActivity(this);
                }
            } else if (CropImage.isReadExternalStoragePermissionsRequired(this, this.mCropImageUri)) {
                requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
            } else {
                this.mCropImageView.setImageUriAsync(this.mCropImageUri);
            }
        }
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            CropImageOptions cropImageOptions = this.mOptions;
            supportActionBar.setTitle((cropImageOptions == null || cropImageOptions.activityTitle == null || this.mOptions.activityTitle.length() <= 0) ? getResources().getString(R.string.crop_image_activity_title) : this.mOptions.activityTitle);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStart() {
        super.onStart();
        this.mCropImageView.setOnSetImageUriCompleteListener(this);
        this.mCropImageView.setOnCropImageCompleteListener(this);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStop() {
        super.onStop();
        this.mCropImageView.setOnSetImageUriCompleteListener(null);
        this.mCropImageView.setOnCropImageCompleteListener(null);
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.crop_image_menu, menu);
        if (!this.mOptions.allowRotation) {
            menu.removeItem(R.id.crop_image_menu_rotate_left);
            menu.removeItem(R.id.crop_image_menu_rotate_right);
        } else if (this.mOptions.allowCounterRotation) {
            menu.findItem(R.id.crop_image_menu_rotate_left).setVisible(true);
        }
        if (!this.mOptions.allowFlipping) {
            menu.removeItem(R.id.crop_image_menu_flip);
        }
        if (this.mOptions.cropMenuCropButtonTitle != null) {
            menu.findItem(R.id.crop_image_menu_crop).setTitle(this.mOptions.cropMenuCropButtonTitle);
        }
        Drawable drawable = null;
        try {
            if (this.mOptions.cropMenuCropButtonIcon != 0) {
                drawable = ContextCompat.getDrawable(this, this.mOptions.cropMenuCropButtonIcon);
                menu.findItem(R.id.crop_image_menu_crop).setIcon(drawable);
            }
        } catch (Exception e) {
            Log.w("AIC", "Failed to read menu crop drawable", e);
        }
        if (this.mOptions.activityMenuIconColor != 0) {
            updateMenuItemIconColor(menu, R.id.crop_image_menu_rotate_left, this.mOptions.activityMenuIconColor);
            updateMenuItemIconColor(menu, R.id.crop_image_menu_rotate_right, this.mOptions.activityMenuIconColor);
            updateMenuItemIconColor(menu, R.id.crop_image_menu_flip, this.mOptions.activityMenuIconColor);
            if (drawable != null) {
                updateMenuItemIconColor(menu, R.id.crop_image_menu_crop, this.mOptions.activityMenuIconColor);
            }
        }
        return true;
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.crop_image_menu_crop) {
            cropImage();
            return true;
        }
        if (menuItem.getItemId() == R.id.crop_image_menu_rotate_left) {
            rotateImage(-this.mOptions.rotationDegrees);
            return true;
        }
        if (menuItem.getItemId() == R.id.crop_image_menu_rotate_right) {
            rotateImage(this.mOptions.rotationDegrees);
            return true;
        }
        if (menuItem.getItemId() == R.id.crop_image_menu_flip_horizontally) {
            this.mCropImageView.flipImageHorizontally();
            return true;
        }
        if (menuItem.getItemId() == R.id.crop_image_menu_flip_vertically) {
            this.mCropImageView.flipImageVertically();
            return true;
        }
        if (menuItem.getItemId() == 16908332) {
            setResultCancel();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        setResultCancel();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        if (i == 200) {
            if (i2 == 0) {
                setResultCancel();
            }
            if (i2 == -1) {
                Uri pickImageResultUri = CropImage.getPickImageResultUri(this, intent);
                this.mCropImageUri = pickImageResultUri;
                if (CropImage.isReadExternalStoragePermissionsRequired(this, pickImageResultUri)) {
                    requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
                } else {
                    this.mCropImageView.setImageUriAsync(this.mCropImageUri);
                }
            }
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (i == 201) {
            Uri uri = this.mCropImageUri;
            if (uri != null && iArr.length > 0 && iArr[0] == 0) {
                this.mCropImageView.setImageUriAsync(uri);
            } else {
                Toast.makeText(this, R.string.crop_image_activity_no_permissions, 1).show();
                setResultCancel();
            }
        }
        if (i == 2011) {
            CropImage.startPickImageActivity(this);
        }
    }

    @Override // com.theartofdev.edmodo.cropper.CropImageView.OnSetImageUriCompleteListener
    public void onSetImageUriComplete(CropImageView cropImageView, Uri uri, Exception exc) {
        if (exc == null) {
            if (this.mOptions.initialCropWindowRectangle != null) {
                this.mCropImageView.setCropRect(this.mOptions.initialCropWindowRectangle);
            }
            if (this.mOptions.initialRotation > -1) {
                this.mCropImageView.setRotatedDegrees(this.mOptions.initialRotation);
                return;
            }
            return;
        }
        setResult(null, exc, 1);
    }

    @Override // com.theartofdev.edmodo.cropper.CropImageView.OnCropImageCompleteListener
    public void onCropImageComplete(CropImageView cropImageView, CropImageView.CropResult cropResult) {
        setResult(cropResult.getUri(), cropResult.getError(), cropResult.getSampleSize());
    }

    protected void cropImage() {
        if (this.mOptions.noOutputImage) {
            setResult(null, null, 1);
        } else {
            this.mCropImageView.saveCroppedImageAsync(getOutputUri(), this.mOptions.outputCompressFormat, this.mOptions.outputCompressQuality, this.mOptions.outputRequestWidth, this.mOptions.outputRequestHeight, this.mOptions.outputRequestSizeOptions);
        }
    }

    protected void rotateImage(int i) {
        this.mCropImageView.rotateImage(i);
    }

    protected Uri getOutputUri() {
        Uri uri = this.mOptions.outputUri;
        if (uri != null && !uri.equals(Uri.EMPTY)) {
            return uri;
        }
        try {
            return Uri.fromFile(File.createTempFile("cropped", this.mOptions.outputCompressFormat == Bitmap.CompressFormat.JPEG ? PictureMimeType.JPG : this.mOptions.outputCompressFormat == Bitmap.CompressFormat.PNG ? PictureMimeType.PNG : PictureMimeType.WEBP, getCacheDir()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to create temp file for output image", e);
        }
    }

    protected void setResult(Uri uri, Exception exc, int i) {
        setResult(exc == null ? -1 : CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE, getResultIntent(uri, exc, i));
        finish();
    }

    protected void setResultCancel() {
        setResult(0);
        finish();
    }

    protected Intent getResultIntent(Uri uri, Exception exc, int i) {
        CropImage.ActivityResult activityResult = new CropImage.ActivityResult(this.mCropImageView.getImageUri(), uri, exc, this.mCropImageView.getCropPoints(), this.mCropImageView.getCropRect(), this.mCropImageView.getRotatedDegrees(), this.mCropImageView.getWholeImageRect(), i);
        Intent intent = new Intent();
        intent.putExtras(getIntent());
        intent.putExtra(CropImage.CROP_IMAGE_EXTRA_RESULT, activityResult);
        return intent;
    }

    private void updateMenuItemIconColor(Menu menu, int i, int i2) {
        Drawable icon;
        MenuItem menuItemFindItem = menu.findItem(i);
        if (menuItemFindItem == null || (icon = menuItemFindItem.getIcon()) == null) {
            return;
        }
        try {
            icon.mutate();
            icon.setColorFilter(i2, PorterDuff.Mode.SRC_ATOP);
            menuItemFindItem.setIcon(icon);
        } catch (Exception e) {
            Log.w("AIC", "Failed to update menu item color", e);
        }
    }
}
