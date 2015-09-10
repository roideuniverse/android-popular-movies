package roide.nanod.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import roide.nanod.popularmovies.fragments.BaseFragment;
import roide.nanod.popularmovies.widgets.SwipeRefreshRecyclerView;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainDiscoveryActivityFragment extends BaseFragment
{
    private SwipeRefreshRecyclerView mSwipeRefreshRecyclerView;

    public MainDiscoveryActivityFragment()
    {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_main_discovery, container, false);
    }

    @Override
    protected void findRequiredViews(View rootView)
    {
        mSwipeRefreshRecyclerView = (SwipeRefreshRecyclerView)
                rootView.findViewById(R.id.main_discovery_swipe_refresh_rv);
    }

    @Override
    protected void prepareViews()
    {
        RecyclerView recyclerView = mSwipeRefreshRecyclerView.getRecyclerView();
        recyclerView.addItemDecoration(new MarginDecoration(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(new NumberedAdapter(30));
    }

    public static class TextViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public TextViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }
    }

    public static class MarginDecoration extends RecyclerView.ItemDecoration {
        private int margin;

        public MarginDecoration(Context context) {
            margin = 50;
        }

        @Override
        public void getItemOffsets(
                Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.set(margin, margin, margin, margin);
        }
    }

    public static class NumberedAdapter extends RecyclerView.Adapter<TextViewHolder> {
        private List<String> labels;

        public NumberedAdapter(int count) {
            labels = new ArrayList<>(count);
            for (int i = 0; i < count; ++i) {
                labels.add(String.valueOf(i));
            }
        }

        @Override
        public TextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            TextView view = new TextView(parent.getContext());
            view.setPadding(20, 20, 20, 20);
            view.setTextColor(Color.WHITE);
            view.setBackgroundColor(Color.BLUE);
            return new TextViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final TextViewHolder holder, final int position) {
            final String label = labels.get(position);
            holder.textView.setText(label);
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(holder.textView.getContext(), label, Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return labels.size();
        }
    }
}
