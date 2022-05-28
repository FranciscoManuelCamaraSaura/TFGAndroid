/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 12/04/21 19:14 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: Launcher.java.
 * Last modified: 25/03/19 16:17.
 */

package com.tfgandroid.schoolmanager.tfg.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavController.OnDestinationChangedListener;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.tfgandroid.schoolmanager.R;
import com.tfgandroid.schoolmanager.data.access.database.AppDatabase;
import com.tfgandroid.schoolmanager.data.access.database.entities.LegalGuardian;
import com.tfgandroid.schoolmanager.data.access.database.entities.Message;
import com.tfgandroid.schoolmanager.data.preferences.Preferences;
import com.tfgandroid.schoolmanager.data.repository.LegalGuardianRepository;
import com.tfgandroid.schoolmanager.databinding.ActivityLauncherBinding;
import com.tfgandroid.schoolmanager.databinding.CloseSessionDialogBinding;
import com.tfgandroid.schoolmanager.databinding.DeleteUnsentMessageDialogBinding;
import com.tfgandroid.schoolmanager.tfg.fragment.NewMessage;
import com.tfgandroid.schoolmanager.tfg.fragment.PreLogin;
import com.tfgandroid.schoolmanager.tfg.fragment.PreviewMessage;
import com.tfgandroid.schoolmanager.tfg.fragment.SubjectFile;
import com.tfgandroid.schoolmanager.tfg.fragment.TeacherFile;
import com.tfgandroid.schoolmanager.tfg.viewmodel.LauncherViewModel;
import com.tfgandroid.schoolmanager.utils.NotificationService;
import com.tfgandroid.schoolmanager.utils.NotificationsChannel;
import com.tfgandroid.schoolmanager.utils.Utils;
import java.util.List;
import java.util.Objects;

public class Launcher extends AppCompatActivity
    implements SharedPreferences.OnSharedPreferenceChangeListener,
        OnClickListener,
        OnDestinationChangedListener {
  public static final boolean INCLUSIVE = false;
  public static final int INDEX = 0;
  public static final String MESSAGE_ID = "messageId";
  public static final String REQUEST_KEY = "PREVIEW_MESSAGE";
  private AlertDialog dialog;
  private AppBarConfiguration appBarConfiguration;
  private boolean serviceBound;
  private DrawerLayout drawerLayout;
  private LauncherViewModel launcherViewModel;
  private List<Message> messagesSaved;
  private NavController navController;
  private NavHostFragment navHostFragment;
  private NavigationView navigationView;
  private NotificationService notificationService;
  private Toolbar toolbar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActivityLauncherBinding activityLauncherBinding =
        ActivityLauncherBinding.inflate(getLayoutInflater());

    setContentView(activityLauncherBinding.getRoot());

    toolbar = activityLauncherBinding.toolbar;
    drawerLayout = activityLauncherBinding.drawerLayout;
    navigationView = activityLauncherBinding.navigationView;

    setSupportActionBar(toolbar);

    navHostFragment =
        (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navigationHostFragment);

    assert navHostFragment != null;

    navController = navHostFragment.getNavController();
    appBarConfiguration =
        new AppBarConfiguration.Builder(navController.getGraph())
            .setOpenableLayout(drawerLayout)
            .build();

    NavigationUI.setupWithNavController(navigationView, navController);
    NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);

    navController.addOnDestinationChangedListener(this);
    navigationView
        .getMenu()
        .findItem(R.id.navigationLogout)
        .setOnMenuItemClickListener(
            item -> {
              createLogoutDialog();
              return true;
            });

    SharedPreferences sharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

    getLegalGuardianPreferences(sharedPreferences);
  }

  @Override
  public void onCreate(
      @Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
    super.onCreate(savedInstanceState, persistentState);
    LegalGuardianRepository.getInstance(getApplication());
  }

  /*@Override
  protected void onStart() {
    super.onStart();

    SharedPreferences sharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    LegalGuardian legalGuardian = Preferences.getLoggedLegalGuardian(sharedPreferences);

    if (Utils.neitherEmptyNorNull(legalGuardian)) {
    Intent intent = new Intent(this, NotificationService.class);

    startService(intent);
    bindService(intent, serviceConnection, 0);

    }
  }*/

  @Override
  protected void onResume() {
    super.onResume();
    PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
        .registerOnSharedPreferenceChangeListener(this);

    SharedPreferences sharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    LegalGuardian legalGuardian = Preferences.getLoggedLegalGuardian(sharedPreferences);

    if (Utils.neitherEmptyNorNull(legalGuardian)) {
      checkNewMessage();
    }
  }

  /*@Override
  protected void onStop() {
    super.onStop();

    SharedPreferences sharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    LegalGuardian legalGuardian = Preferences.getLoggedLegalGuardian(sharedPreferences);

    if (Utils.neitherEmptyNorNull(legalGuardian)) {
    if (serviceBound) {
      if (notificationService.isServiceRunning()) {
        notificationService.startService(this, launcherViewModel, legalGuardian);
      } else {
        stopService(new Intent(this, NotificationService.class));
      }

      unbindService(serviceConnection);
      serviceBound = false;
    }
    }
  }*/

  @Override
  protected void onPause() {
    super.onPause();
    PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
        .unregisterOnSharedPreferenceChangeListener(this);

    SharedPreferences sharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    LegalGuardian legalGuardian = Preferences.getLoggedLegalGuardian(sharedPreferences);

    if (Utils.neitherEmptyNorNull(legalGuardian)) {
      checkNewMessage();
    }
  }

  /*private final ServiceConnection serviceConnection =
      new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
          notificationService = ((NotificationService.NotificationBinder) service).getService();
          notificationService.background();
          serviceBound = true;
        }

        public void onServiceDisconnected(ComponentName className) {
          notificationService = null;
          serviceBound = false;
        }
      };*/

  @Override
  public void onBackPressed() {
    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
      drawerLayout.closeDrawer(GravityCompat.START);
    } else {
      if (navHostFragment.getChildFragmentManager().getFragments().get(0) instanceof SubjectFile) {
        navController.popBackStack(R.id.subjects, INCLUSIVE);
      } else if (navHostFragment.getChildFragmentManager().getFragments().get(0)
          instanceof TeacherFile) {
        navController.popBackStack(R.id.teachers, INCLUSIVE);
      } else if (navHostFragment.getChildFragmentManager().getFragments().get(0)
          instanceof NewMessage) {
        Fragment newMessage = navHostFragment.getChildFragmentManager().getFragments().get(0);

        assert newMessage.getParentFragment() != null;

        TextInputLayout newMessageEditTextMessageTitle =
            newMessage.getParentFragment().requireView().findViewById(R.id.editTextMessageTitle);
        TextInputLayout newMessageEditTextMessageContent =
            newMessage.getParentFragment().requireView().findViewById(R.id.editTextMessageContent);

        checkMessageData(newMessageEditTextMessageTitle, newMessageEditTextMessageContent);
      } else if (navHostFragment.getChildFragmentManager().getFragments().get(0)
          instanceof PreviewMessage) {
        sendMessageData();
        super.onBackPressed();
      } else if (navHostFragment.getChildFragmentManager().getFragments().get(0)
          instanceof PreLogin) {
        finish();
      } else {
        super.onBackPressed();
      }
    }
  }

  @Override
  public void onClick(View view) {
    if (view.getId() == R.id.buttonAccept) {
      super.onBackPressed();
      dialog.cancel();
    } else if (view.getId() == R.id.buttonLogout) {
      SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
      Preferences.setLogout(sharedPreferences);
      AppDatabase.deleteDataBase();
      dialog.cancel();
      Navigation.findNavController(this, R.id.navigationHostFragment).navigate(R.id.toPreLogin);
    } else if (view.getId() == R.id.buttonCancel) {
      dialog.cancel();
    }
  }

  @Override
  public void onDestinationChanged(
      @NonNull NavController controller,
      @NonNull NavDestination destination,
      @Nullable Bundle arguments) {
    if (destination.getId() == R.id.preLogin || destination.getId() == R.id.loginFragment) {
      toolbar.setVisibility(View.GONE);
      drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    } else {
      toolbar.setVisibility(View.VISIBLE);
      drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }
  }

  @Override
  public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    if (key.equals(Preferences.PREF_LEGAL_GUARDIAN)) {
      getLegalGuardianPreferences(sharedPreferences);
    }
  }

  @Override
  public boolean onSupportNavigateUp() {
    return NavigationUI.navigateUp(navController, appBarConfiguration)
        || super.onSupportNavigateUp();
  }

  private void getLegalGuardianPreferences(SharedPreferences sharedPreferences) {
    LegalGuardian legalGuardian = Preferences.getLoggedLegalGuardian(sharedPreferences);

    if (Utils.neitherEmptyNorNull(legalGuardian)) {
      setDrawerHeaderName(legalGuardian.getStudent_name());
    }
  }

  private void checkNewMessage() {
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    LegalGuardian legalGuardian = Preferences.getLoggedLegalGuardian(sharedPreferences);
    launcherViewModel = new ViewModelProvider(this).get(LauncherViewModel.class);

    launcherViewModel
        .getMessagesSaved(legalGuardian)
        .observe(this, messages -> messagesSaved = messages);

    launcherViewModel
        .getMessages(legalGuardian)
        .observeForever(
            messages -> {
              Message message = messages.get(messages.size() - 1);

              if (!messagesSaved.contains(message) && !message.isRead()) {
                setNotification(message);
              }
            });
  }

  private void checkMessageData(
      TextInputLayout newMessageEditTextMessageTitle,
      TextInputLayout newMessageEditTextMessageContent) {
    if (!Objects.requireNonNull(newMessageEditTextMessageTitle.getEditText())
            .getText()
            .toString()
            .isEmpty()
        || !Objects.requireNonNull(newMessageEditTextMessageContent.getEditText())
            .getText()
            .toString()
            .isEmpty()) {
      createDeleteUnsentMessageDialog();
    } else {
      super.onBackPressed();
    }
  }

  private void createDeleteUnsentMessageDialog() {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    DeleteUnsentMessageDialogBinding deleteUnsentMessageDialogBinding =
        DeleteUnsentMessageDialogBinding.inflate(getLayoutInflater());

    builder.setView(deleteUnsentMessageDialogBinding.getRoot());

    Button buttonAccept = deleteUnsentMessageDialogBinding.buttonAccept;
    Button buttonCancel = deleteUnsentMessageDialogBinding.buttonCancel;

    buttonAccept.setOnClickListener(this);
    buttonCancel.setOnClickListener(this);
    dialog = builder.create();
    dialog.show();
  }

  private void sendMessageData() {
    Fragment previewMessage = navHostFragment.getChildFragmentManager().getFragments().get(0);

    assert previewMessage.getParentFragment() != null;

    TextView name =
        previewMessage.getParentFragment().requireView().findViewById(R.id.textViewName);
    TextView subject =
        previewMessage.getParentFragment().requireView().findViewById(R.id.textViewSubject);
    TextView messageTitle =
        previewMessage.getParentFragment().requireView().findViewById(R.id.textViewMessageTitle);
    TextView messageContent =
        previewMessage.getParentFragment().requireView().findViewById(R.id.textViewMessageContent);

    Bundle result = PreviewMessage.onButtonBackIsPress(name, subject, messageTitle, messageContent);
    navHostFragment.getChildFragmentManager().setFragmentResult(REQUEST_KEY, result);
  }

  private void setDrawerHeaderName(String studentName) {
    TextView headerName = navigationView.getHeaderView(INDEX).findViewById(R.id.drawerHeaderName);

    headerName.setText(studentName);
  }

  private void setNotification(Message message) {
    NotificationsChannel notification = new NotificationsChannel(this);
    Bundle bundle = new Bundle();

    bundle.putInt(MESSAGE_ID, message.getId());

    notification.createNotification(this, bundle, message.getMatter(), message.getText());
  }

  private void createLogoutDialog() {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    CloseSessionDialogBinding closeSessionDialogBinding =
        CloseSessionDialogBinding.inflate(getLayoutInflater());

    builder.setView(closeSessionDialogBinding.getRoot());

    Button buttonAccept = closeSessionDialogBinding.buttonLogout;
    Button buttonCancel = closeSessionDialogBinding.buttonCancel;

    buttonAccept.setOnClickListener(this);
    buttonCancel.setOnClickListener(this);
    dialog = builder.create();
    dialog.show();
  }
}
