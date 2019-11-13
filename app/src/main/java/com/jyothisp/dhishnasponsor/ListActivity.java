package com.jyothisp.dhishnasponsor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ListActivity extends AppCompatActivity {

    private FirestoreRecyclerAdapter<Company, CompanyViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        String uid = FirebaseAuth.getInstance().getUid();

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        Query query = rootRef.collection("companies")
                .whereEqualTo("uid", uid);


        FirestoreRecyclerOptions<Company> options = new FirestoreRecyclerOptions.Builder<Company>()
                .setQuery(query, Company.class)
                .build();


        adapter = new FirestoreRecyclerAdapter<Company, CompanyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CompanyViewHolder companyViewHolder, int position, @NonNull Company productModel) {
                companyViewHolder.setProductName(productModel);
            }

            @NonNull
            @Override
            public CompanyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
                return new CompanyViewHolder(view);
            }
        };

        recyclerView.setAdapter(adapter);
    }


    private class CompanyViewHolder extends RecyclerView.ViewHolder {
        private View view;

        CompanyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }

        void setProductName(Company company) {

            final LinearLayout container = view.findViewById(R.id.item_container);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    container.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                }
            });
            TextView textViewName = view.findViewById(R.id.cname_text_view);
            textViewName.setText(company.getName());
            TextView textViewLocale = view.findViewById(R.id.locale_text_view);
            textViewLocale.setText(company.getLocality());
            TextView textViewStatus = view.findViewById(R.id.status_text_view);
            textViewStatus.setText(company.getStatus());
            final String phone = company.getPhone();

            SimpleDateFormat formatter = new SimpleDateFormat("dd MMM HH:mm");
            Date date = new Date(company.getTime());
            String time = formatter.format(date);

            TextView textViewTime = view.findViewById(R.id.time_text_view);
            textViewTime.setText(time);

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (phone != null) {
                        String posted_by = phone;
                        String uri = "tel:" + posted_by.trim();
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse(uri));
                        startActivity(intent);
                        return true;
                    } else {
                        Toast.makeText(getApplicationContext(), "Company contact unavailable", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
            });

        }
    }


    private void editDoc() {
        Toast.makeText(getApplicationContext(), "edit", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (adapter != null) {
            adapter.stopListening();
        }
    }
}
