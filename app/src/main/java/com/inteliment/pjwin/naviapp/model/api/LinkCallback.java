package com.inteliment.pjwin.naviapp.model.api;

import com.jude.http.RequestListener;

/**
 * Created by hans on 20-Mar-16.
 */
class LinkCallback implements RequestListener {
    private LinkCallback link;
    public LinkCallback add(LinkCallback other){
        other.setLink(this);
        return other;
    }
    private void setLink(LinkCallback link){
        this.link = link;
    }

    @Override
    public void onRequest() {
        if (link != null)
            link.onRequest();
    }

    @Override
    public void onSuccess(String s) {
        if (link != null)
            link.onSuccess(s);
    }

    @Override
    public void onError(String s) {
        if (link != null)
            link.onError(s);
    }
}
