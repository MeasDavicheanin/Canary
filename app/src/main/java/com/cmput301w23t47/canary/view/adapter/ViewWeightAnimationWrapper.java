package com.cmput301w23t47.canary.view.adapter;

import android.view.View;
import android.widget.LinearLayout;

public class ViewWeightAnimationWrapper {
	private View view;
	
	public ViewWeightAnimationWrapper(View view) {
		if(view.getLayoutParams() instanceof LinearLayout.LayoutParams) {
			this.view = view;
		} else {
			throw new IllegalArgumentException("The view is not a LinearLayout");
		}
	}
	
	public void setWeight(float weight) {
		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
		params.weight = weight;
		view.setLayoutParams(params);
	}
	
	public float getWeight() {
		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
		return params.weight;
	}
	
	
	
}
