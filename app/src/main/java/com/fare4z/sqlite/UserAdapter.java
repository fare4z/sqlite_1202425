package com.fare4z.sqlite;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    Context context;
    ArrayList<UserData> userList;
    UserDataSource userDataSource;

    public UserAdapter(Context context, ArrayList<UserData> userList)  {
        this.context = context;
        this.userList = userList;
        userDataSource = new UserDataSource(context);
        userDataSource.open();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.userlist_view,parent,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UserData userData = userList.get(position);
        holder.tvOutputID.setText("ID " + userData.getId());
        holder.tvOutputName.setText("Name " + userData.getName());
        holder.tvOutputPassword.setText("Password " + userData.getPassword());

        holder.btnRvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Test "+userData.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnRvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDataSource.deleteUser(userData.getId());
                userList.remove(position); // Remove the item from the list
                notifyItemRemoved(position); // Notify RecyclerView that the item was removed
                notifyItemRangeChanged(position, userList.size()); // Update the position of remaining items


            }
        });

        holder.btnRvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an AlertDialog builder
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Update User");

                View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_update_user, null);
                builder.setView(dialogView);


                EditText etNameUpd = dialogView.findViewById(R.id.etNameUpd);
                EditText etPasswordUpd = dialogView.findViewById(R.id.etPasswordUpd);

                // Pre-fill the fields with current data
                etNameUpd.setText(userData.getName());
                etPasswordUpd.setText(userData.getPassword());

                // Set up the Update button
                builder.setPositiveButton("Update", (dialog, which) -> {
                    // Capture input data
                    String updatedName = etNameUpd.getText().toString();
                    String updatedPassword = etPasswordUpd.getText().toString();

                    // Update the user data in the database and userList
                    userData.setName(updatedName);
                    userData.setPassword(updatedPassword);
                    userDataSource.updateUserData(userData.getId(), updatedName, updatedPassword); // Update in the database
                    notifyItemChanged(position); // Refresh the RecyclerView item
                });

                // Set up the Cancel button
               // builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                // Show the dialog
                builder.create().show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tvOutputID, tvOutputName, tvOutputPassword;
        Button btnRvUpdate , btnRvDelete;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOutputID = itemView.findViewById(R.id.tvOutputID);
            tvOutputName = itemView.findViewById(R.id.tvOutputName);
            tvOutputPassword = itemView.findViewById(R.id.tvOutputPassword);

            btnRvUpdate = itemView.findViewById(R.id.btnRvUpdate);
            btnRvDelete = itemView.findViewById(R.id.btnRvDelete);
        }
    }

}
