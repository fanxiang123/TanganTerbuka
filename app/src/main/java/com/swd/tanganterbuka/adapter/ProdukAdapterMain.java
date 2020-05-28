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
import com.swd.tanganterbuka.R;
import com.swd.tanganterbuka.activity.AppDetailsActivity;
import com.swd.tanganterbuka.modle.AppModle;
import com.swd.tanganterbuka.modle.Produk;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

//主页的列表
public class ProdukAdapterMain extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private final ArrayList<AppModle> mData;
    private int mType = 0;

    public enum ITEM_TYPE {
        ITEM_TYPE_MAIN,
        ITEM_TYPE_ITEM
    }


    public ProdukAdapterMain(Context context, ArrayList<AppModle> data, int type) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
        mData = data;
        mType = type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM_TYPE_MAIN.ordinal()) {
            return new ProdukAdapterMain.MainItem(mLayoutInflater.inflate(R.layout.produk_main_item, parent, false));
        } else {
            return new ProdukAdapterMain.ListItem(mLayoutInflater.inflate(R.layout.produk_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int pos) {
        if (holder instanceof ProdukAdapterMain.ListItem) {
            final AppModle produk = mData.get(pos);
            ((ListItem) holder).name.setText(produk.getProductName());
            ((ListItem) holder).num.setText(produk.getTotalScore());
            ((ProdukAdapterMain.ListItem) holder).money.setText("Rp " + produk.getPriceMin()+" ~ "+"Rp " + produk.getPriceMax());
            Glide.with(mContext).
                    load(produk.getIcon())
                    .asBitmap().fitCenter() //刷新后变形问题
                    .placeholder(R.mipmap.ic_launcher)
                    .into(((ListItem) holder).icon);
            ((ListItem) holder).btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(mContext, AppDetailsActivity.class);
                    intent.setAction(produk.getId()+"");
                    mContext.startActivity(intent);
                }
            });
            ((ListItem) holder).ll01.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(mContext, AppDetailsActivity.class);
                    intent.setAction(produk.getId()+"");
                    mContext.startActivity(intent);
                }
            });
        } else if (holder instanceof ProdukAdapterMain.MainItem) {
            if (mType == 0)
                ((ProdukAdapterMain.MainItem) holder).banner.
                        setBackgroundResource(R.mipmap.mudahdisetujui);
            else
                ((ProdukAdapterMain.MainItem) holder).banner.
                        setBackgroundResource(R.mipmap.produkterbaru);
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
            icon = itemView.findViewById(R.id.icon);
            ll01 = itemView.findViewById(R.id.ll01);
            btn = itemView.findViewById(R.id.btn);
        }
    }

    //顶部
    public class MainItem extends RecyclerView.ViewHolder {
        ImageView banner;
        MainItem(View view) {
            super(view);
            banner = itemView.findViewById(R.id.banner);
        }
    }
}
