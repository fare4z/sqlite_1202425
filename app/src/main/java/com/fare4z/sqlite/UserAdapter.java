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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
Context context;
ArrayList<UserData> userList;
UserDataSource userDataSource;

public UserAdapter(Context context, ArrayList<UserData> userList) {
    this.context = context;
    this.userList = userList;
    userDataSource = new UserDataSource(context);
    userDataSource.open();
}

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.userlist_view,
                parent ,
                false);

        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UserData userData = userList.get(position);
        holder.tvOutputID.setText("ID " + userData.getId());
        holder.tvOutputName.setText("Name " + userData.getName());
        holder.tvOutputPassword.setText("Password " + userData.getPassword());

        holder.btnRvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userDataSource.deleteUser(userData.getId());
                userList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position , userList.size());
            }
        });

        holder.btnRvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Update User");

                View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_update_user, null);
                builder.setView(dialogView);

                EditText etNameUpd = dialogView.findViewById(R.id.etNameUpd);
                EditText etPasswordUpd = dialogView.findViewById(R.id.etPasswordUpd);

                etNameUpd.setText(userData.getName());
                etPasswordUpd.setText(userData.getPassword());

                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    String updatedName = etNameUpd.getText().toString();
                    String updatedPassword = etPasswordUpd.getText().toString();

                    userData.setName(updatedName);
                    userData.setPassword(updatedPassword);

                    userDataSource.updateUserData(userData.getId(), updatedName,updatedPassword);
                    notifyItemChanged(position);
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });

                builder.create().show();

            }
        });




    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
TextView tvOutputID , tvOutputName, tvOutputPassword;
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
