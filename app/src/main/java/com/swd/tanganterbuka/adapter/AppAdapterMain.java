package com.swd.tanganterbuka.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.swd.tanganterbuka.R;
import com.swd.tanganterbuka.activity.AppDetailsActivity;
import com.swd.tanganterbuka.activity.HomeActivity;
import com.swd.tanganterbuka.activity.ProdukActivity;
import com.swd.tanganterbuka.modle.AppModle;
import com.swd.tanganterbuka.modle.BaseResponse;
import com.swd.tanganterbuka.modle.Debit;
import com.swd.tanganterbuka.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

//主页的列表
public class AppAdapterMain extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private final List<AppModle> mData;

    public enum ITEM_TYPE {
        ITEM_TYPE_MAIN,
        ITEM_TYPE_ITEM
    }


    public AppAdapterMain(Context context, List<AppModle> data) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
        mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM_TYPE_MAIN.ordinal()) {
            return new AppAdapterMain.MainItem(mLayoutInflater.inflate(R.layout.main_item, parent, false));
        } else {
            return new AppAdapterMain.ListItem(mLayoutInflater.inflate(R.layout.app_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int pos) {
        if (holder instanceof AppAdapterMain.ListItem) {
            final AppModle debit = mData.get(pos);
            ((AppAdapterMain.ListItem) holder).name.setText(debit.getProductName());
            ((AppAdapterMain.ListItem) holder).num.setText(debit.getTotalScore());
            ((AppAdapterMain.ListItem) holder).money.setText("Rp " + debit.getPriceMax());
            ((AppAdapterMain.ListItem) holder).interest.setText("" + debit.getInterestRate() + "% hari");
            ((AppAdapterMain.ListItem) holder).time.setText("" + debit.getLoanDay() + " hari");
            Glide.with(mContext).
                    load(debit.getIcon())
                    .asBitmap().fitCenter() //刷新后变形问题
                    .placeholder(R.mipmap.ic_launcher)
                    .into(((ListItem) holder).icon);
            ((AppAdapterMain.ListItem) holder).btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(mContext, AppDetailsActivity.class);
                    intent.setAction(debit.getId()+"");
                    mContext.startActivity(intent);
                }
            }); ((AppAdapterMain.ListItem) holder).ll01.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(mContext, AppDetailsActivity.class);
                    intent.setAction(debit.getId()+"");
                    mContext.startActivity(intent);
                }
            });
        } else if (holder instanceof AppAdapterMain.MainItem) {
            ((AppAdapterMain.MainItem) holder).my.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(mContext, HomeActivity.class);
                    mContext.startActivity(intent);
                }
            });
            ((AppAdapterMain.MainItem) holder).btn01.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(mContext, ProdukActivity.class);
                    intent.setAction("0");
                    mContext.startActivity(intent);
                }
            });
            ((AppAdapterMain.MainItem) holder).btn02.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(mContext, ProdukActivity.class);
                    intent.setAction("1");
                    mContext.startActivity(intent);
                }
            });
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ITEM_TYPE.ITEM_TYPE_MAIN.ordinal();
        } else {
            return ITEM_TYPE.ITEM_TYPE_ITEM.ordinal();
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    //列表
    class ListItem extends RecyclerView.ViewHolder {
        TextView name, num, money, interest, time;
        ImageView icon;
        Button btn;
        LinearLayout ll01;

        ListItem(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            num = itemView.findViewById(R.id.num);
            money = itemView.findViewById(R.id.money);
            interest = itemView.findViewById(R.id.interest);
            time = itemView.findViewById(R.id.time);
            icon = itemView.findViewById(R.id.icon);
            btn = itemView.findViewById(R.id.btn);
            ll01 = itemView.findViewById(R.id.ll01);
        }
    }

    //顶部
    public class MainItem extends RecyclerView.ViewHolder {
        ImageView my;
        Button btn01,btn02;
        MainItem(View view) {
            super(view);
            my = itemView.findViewById(R.id.my);
            btn01 = itemView.findViewById(R.id.btn01);
            btn02 = itemView.findViewById(R.id.btn02);
        }
    }
}
