/*
 * ******************************************************************************
 *  * Copyright 2015 See AUTHORS file.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *   http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *  *****************************************************************************
 */

package com.uwsoft.editor.gdx.ui.layer;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.uwsoft.editor.data.manager.DataManager;
import com.uwsoft.editor.data.manager.TextureManager;
import com.uwsoft.editor.gdx.sandbox.Sandbox;
import com.uwsoft.editor.renderer.data.LayerItemVO;

public class LayerItem extends Group {

    private final DataManager dataManager;
    private Image bgImg;

    private Image lock;
    private Image eye;

    private LayerItemVO layerItemVo;
    private Sandbox sandbox;
    private Image rowSeparator;

    public LayerItem(LayerItemVO vo, Sandbox sandbox) {

        this.layerItemVo = vo;
        this.sandbox = sandbox;

        setWidth(247);
        setHeight(20);
        dataManager = DataManager.getInstance();
        bgImg = new Image(dataManager.textureManager.getEditorAsset("pixel"));
        bgImg.setColor(0.425f, 0.425f, 0.425f, 1.0f);
        bgImg.setScaleX(getWidth());
        bgImg.setScaleY(getHeight());
        addActor(bgImg);

        Image bottom = new Image(dataManager.textureManager.getEditorAsset("pixel"));
        bottom.setColor(0.125f, 0.125f, 0.125f, 1.0f);
        bottom.setScaleX(getWidth());
        addActor(bottom);

        Image sep1 = new Image(dataManager.textureManager.getEditorAsset("pixel"));
        sep1.setColor(0.225f, 0.225f, 0.225f, 1.0f);
        sep1.setScaleY(getHeight());
        sep1.setX(20);
        addActor(sep1);

        Image sep2 = new Image(dataManager.textureManager.getEditorAsset("pixel"));
        sep2.setColor(0.225f, 0.225f, 0.225f, 1.0f);
        sep2.setScaleY(getHeight());
        sep2.setX(40);
        addActor(sep2);

        lock = new Image(dataManager.textureManager.getEditorAsset("lock"));
        lock.setX(4);
        lock.setY(4);
        addActor(lock);

        eye = new Image(dataManager.textureManager.getEditorAsset("eye"));
        eye.setX(24);
        eye.setY(5);
        addActor(eye);

        Label lbl = new Label(layerItemVo.layerName, dataManager.textureManager.editorSkin);
        lbl.setX(44);
        lbl.setY(4);
        addActor(lbl);

        updateUI();

    }

    public LayerItem(LayerItem layerItem) {
        this(layerItem.layerItemVo, layerItem.sandbox);
    }

    public String getLayerName() {
        return layerItemVo.layerName;
    }

    public void initListeners() {
        lock.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                layerItemVo.isLocked = !layerItemVo.isLocked;
                updateUI();
                sandbox.getCurrentScene().reAssembleLayers();
                sandbox.getUIStage().getLayerPanel().initContent();
            }
        });
        eye.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                layerItemVo.isVisible = !layerItemVo.isVisible;
                updateUI();
                sandbox.getCurrentScene().reAssembleLayers();
                sandbox.getUIStage().getLayerPanel().initContent();
            }
        });
    }

    public void showLayerRowSeparator() {
        if (rowSeparator == null) {
            rowSeparator = new Image(dataManager.textureManager.getEditorAsset("pixel"));
            rowSeparator.setColor(0.97f, 0.97f, 0.98f, 1.0f);
            rowSeparator.setScaleX(getWidth());
            rowSeparator.setScaleY(2);
        }
        addActor(rowSeparator);
    }

    public void hideLayerRowSeparator() {
        removeActor(rowSeparator);
    }

    public void select() {
        bgImg.setColor(90f / 255f, 102f / 255f, 121f / 255f, 1.0f);
    }

    public void unselect() {
        bgImg.setColor(0.425f, 0.425f, 0.425f, 1.0f);
    }

    public void updateUI() {
        if (!layerItemVo.isLocked) {
            lock.getColor().a = 0.5f;
        } else {
            lock.getColor().a = 1;
        }
        if (!layerItemVo.isVisible) {
            eye.getColor().a = 0.5f;
        } else {
            eye.getColor().a = 1;
        }
    }

}
