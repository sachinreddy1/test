/*
 * Copyright 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.timelineview;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * A wrapper class for ItemAnimator that records View bounds and decides whether it should run
 * move, change, add or remove animations. This class also replicates the original ItemAnimator
 * API.
 * <p>
 * It uses {@link TimelineView.ItemAnimator.ItemHolderInfo} to track the bounds information of the Views. If you would like
 * to
 * extend this class, you can override {@link #obtainHolderInfo()} method to provide your own info
 * class that extends {@link TimelineView.ItemAnimator.ItemHolderInfo}.
 */
public abstract class SimpleItemAnimator extends TimelineView.ItemAnimator {

    private static final boolean DEBUG = false;

    private static final String TAG = "SimpleItemAnimator";

    boolean mSupportsChangeAnimations = true;

    /**
     * Returns whether this ItemAnimator supports animations of change events.
     *
     * @return true if change animations are supported, false otherwise
     */
    @SuppressWarnings("unused")
    public boolean getSupportsChangeAnimations() {
        return mSupportsChangeAnimations;
    }

    /**
     * Sets whether this ItemAnimator supports animations of item change events.
     * If you set this property to false, actions on the data set which change the
     * contents of items will not be animated. What those animations do is left
     * up to the discretion of the ItemAnimator subclass, in its
     * {@link #animateChange(TimelineView.ViewHolder, TimelineView.ViewHolder, int, int, int, int)} implementation.
     * The value of this property is true by default.
     *
     * @param supportsChangeAnimations true if change animations are supported by
     *                                 this ItemAnimator, false otherwise. If the property is false,
     *                                 the ItemAnimator
     *                                 will not receive a call to
     *                                 {@link #animateChange(TimelineView.ViewHolder, TimelineView.ViewHolder, int, int, int,
     *                                 int)} when changes occur.
     * @see TimelineView.Adapter#notifyItemChanged(int)
     * @see TimelineView.Adapter#notifyItemRangeChanged(int, int)
     */
    public void setSupportsChangeAnimations(boolean supportsChangeAnimations) {
        mSupportsChangeAnimations = supportsChangeAnimations;
    }

    /**
     * {@inheritDoc}
     *
     * @return True if change animations are not supported or the ViewHolder is invalid,
     * false otherwise.
     *
     * @see #setSupportsChangeAnimations(boolean)
     */
    @Override
    public boolean canReuseUpdatedViewHolder(@NonNull TimelineView.ViewHolder viewHolder) {
        return !mSupportsChangeAnimations || viewHolder.isInvalid();
    }

    @Override
    public boolean animateDisappearance(@NonNull TimelineView.ViewHolder viewHolder,
            @NonNull ItemHolderInfo preLayoutInfo, @Nullable ItemHolderInfo postLayoutInfo) {
        int oldLeft = preLayoutInfo.left;
        int oldTop = preLayoutInfo.top;
        View disappearingItemView = viewHolder.itemView;
        int newLeft = postLayoutInfo == null ? disappearingItemView.getLeft() : postLayoutInfo.left;
        int newTop = postLayoutInfo == null ? disappearingItemView.getTop() : postLayoutInfo.top;
        if (!viewHolder.isRemoved() && (oldLeft != newLeft || oldTop != newTop)) {
            disappearingItemView.layout(newLeft, newTop,
                    newLeft + disappearingItemView.getWidth(),
                    newTop + disappearingItemView.getHeight());
            if (DEBUG) {
                Log.d(TAG, "DISAPPEARING: " + viewHolder + " with view " + disappearingItemView);
            }
            return animateMove(viewHolder, oldLeft, oldTop, newLeft, newTop);
        } else {
            if (DEBUG) {
                Log.d(TAG, "REMOVED: " + viewHolder + " with view " + disappearingItemView);
            }
            return animateRemove(viewHolder);
        }
    }

    @Override
    public boolean animateAppearance(@NonNull TimelineView.ViewHolder viewHolder,
            @Nullable ItemHolderInfo preLayoutInfo, @NonNull ItemHolderInfo postLayoutInfo) {
        if (preLayoutInfo != null && (preLayoutInfo.left != postLayoutInfo.left
                || preLayoutInfo.top != postLayoutInfo.top)) {
            // slide items in if before/after locations differ
            if (DEBUG) {
                Log.d(TAG, "APPEARING: " + viewHolder + " with view " + viewHolder);
            }
            return animateMove(viewHolder, preLayoutInfo.left, preLayoutInfo.top,
                    postLayoutInfo.left, postLayoutInfo.top);
        } else {
            if (DEBUG) {
                Log.d(TAG, "ADDED: " + viewHolder + " with view " + viewHolder);
            }
            return animateAdd(viewHolder);
        }
    }

    @Override
    public boolean animatePersistence(@NonNull TimelineView.ViewHolder viewHolder,
            @NonNull ItemHolderInfo preInfo, @NonNull ItemHolderInfo postInfo) {
        if (preInfo.left != postInfo.left || preInfo.top != postInfo.top) {
            if (DEBUG) {
                Log.d(TAG, "PERSISTENT: " + viewHolder
                        + " with view " + viewHolder.itemView);
            }
            return animateMove(viewHolder,
                    preInfo.left, preInfo.top, postInfo.left, postInfo.top);
        }
        dispatchMoveFinished(viewHolder);
        return false;
    }

    @Override
    public boolean animateChange(@NonNull TimelineView.ViewHolder oldHolder, @NonNull TimelineView.ViewHolder newHolder,
                                 @NonNull ItemHolderInfo preInfo, @NonNull ItemHolderInfo postInfo) {
        if (DEBUG) {
            Log.d(TAG, "CHANGED: " + oldHolder + " with view " + oldHolder.itemView);
        }
        final int fromLeft = preInfo.left;
        final int fromTop = preInfo.top;
        final int toLeft, toTop;
        if (newHolder.shouldIgnore()) {
            toLeft = preInfo.left;
            toTop = preInfo.top;
        } else {
            toLeft = postInfo.left;
            toTop = postInfo.top;
        }
        return animateChange(oldHolder, newHolder, fromLeft, fromTop, toLeft, toTop);
    }

    /**
     * Called when an item is removed from the RecyclerView. Implementors can choose
     * whether and how to animate that change, but must always call
     * {@link #dispatchRemoveFinished(TimelineView.ViewHolder)} when done, either
     * immediately (if no animation will occur) or after the animation actually finishes.
     * The return value indicates whether an animation has been set up and whether the
     * ItemAnimator's {@link #runPendingAnimations()} method should be called at the
     * next opportunity. This mechanism allows ItemAnimator to set up individual animations
     * as separate calls to {@link #animateAdd(TimelineView.ViewHolder) animateAdd()},
     * {@link #animateMove(TimelineView.ViewHolder, int, int, int, int) animateMove()},
     * {@link #animateRemove(TimelineView.ViewHolder) animateRemove()}, and
     * {@link #animateChange(TimelineView.ViewHolder, TimelineView.ViewHolder, int, int, int, int)} come in one by one,
     * then start the animations together in the later call to {@link #runPendingAnimations()}.
     *
     * <p>This method may also be called for disappearing items which continue to exist in the
     * RecyclerView, but for which the system does not have enough information to animate
     * them out of view. In that case, the default animation for removing items is run
     * on those items as well.</p>
     *
     * @param holder The item that is being removed.
     * @return true if a later call to {@link #runPendingAnimations()} is requested,
     * false otherwise.
     */
    public abstract boolean animateRemove(TimelineView.ViewHolder holder);

    /**
     * Called when an item is added to the RecyclerView. Implementors can choose
     * whether and how to animate that change, but must always call
     * {@link #dispatchAddFinished(TimelineView.ViewHolder)} when done, either
     * immediately (if no animation will occur) or after the animation actually finishes.
     * The return value indicates whether an animation has been set up and whether the
     * ItemAnimator's {@link #runPendingAnimations()} method should be called at the
     * next opportunity. This mechanism allows ItemAnimator to set up individual animations
     * as separate calls to {@link #animateAdd(TimelineView.ViewHolder) animateAdd()},
     * {@link #animateMove(TimelineView.ViewHolder, int, int, int, int) animateMove()},
     * {@link #animateRemove(TimelineView.ViewHolder) animateRemove()}, and
     * {@link #animateChange(TimelineView.ViewHolder, TimelineView.ViewHolder, int, int, int, int)} come in one by one,
     * then start the animations together in the later call to {@link #runPendingAnimations()}.
     *
     * <p>This method may also be called for appearing items which were already in the
     * RecyclerView, but for which the system does not have enough information to animate
     * them into view. In that case, the default animation for adding items is run
     * on those items as well.</p>
     *
     * @param holder The item that is being added.
     * @return true if a later call to {@link #runPendingAnimations()} is requested,
     * false otherwise.
     */
    public abstract boolean animateAdd(TimelineView.ViewHolder holder);

    /**
     * Called when an item is moved in the RecyclerView. Implementors can choose
     * whether and how to animate that change, but must always call
     * {@link #dispatchMoveFinished(TimelineView.ViewHolder)} when done, either
     * immediately (if no animation will occur) or after the animation actually finishes.
     * The return value indicates whether an animation has been set up and whether the
     * ItemAnimator's {@link #runPendingAnimations()} method should be called at the
     * next opportunity. This mechanism allows ItemAnimator to set up individual animations
     * as separate calls to {@link #animateAdd(TimelineView.ViewHolder) animateAdd()},
     * {@link #animateMove(TimelineView.ViewHolder, int, int, int, int) animateMove()},
     * {@link #animateRemove(TimelineView.ViewHolder) animateRemove()}, and
     * {@link #animateChange(TimelineView.ViewHolder, TimelineView.ViewHolder, int, int, int, int)} come in one by one,
     * then start the animations together in the later call to {@link #runPendingAnimations()}.
     *
     * @param holder The item that is being moved.
     * @return true if a later call to {@link #runPendingAnimations()} is requested,
     * false otherwise.
     */
    public abstract boolean animateMove(TimelineView.ViewHolder holder, int fromX, int fromY,
                                        int toX, int toY);

    /**
     * Called when an item is changed in the RecyclerView, as indicated by a call to
     * {@link TimelineView.Adapter#notifyItemChanged(int)} or
     * {@link TimelineView.Adapter#notifyItemRangeChanged(int, int)}.
     * <p>
     * Implementers can choose whether and how to animate changes, but must always call
     * {@link #dispatchChangeFinished(TimelineView.ViewHolder, boolean)} for each non-null distinct ViewHolder,
     * either immediately (if no animation will occur) or after the animation actually finishes.
     * If the {@code oldHolder} is the same ViewHolder as the {@code newHolder}, you must call
     * {@link #dispatchChangeFinished(TimelineView.ViewHolder, boolean)} once and only once. In that case, the
     * second parameter of {@code dispatchChangeFinished} is ignored.
     * <p>
     * The return value indicates whether an animation has been set up and whether the
     * ItemAnimator's {@link #runPendingAnimations()} method should be called at the
     * next opportunity. This mechanism allows ItemAnimator to set up individual animations
     * as separate calls to {@link #animateAdd(TimelineView.ViewHolder) animateAdd()},
     * {@link #animateMove(TimelineView.ViewHolder, int, int, int, int) animateMove()},
     * {@link #animateRemove(TimelineView.ViewHolder) animateRemove()}, and
     * {@link #animateChange(TimelineView.ViewHolder, TimelineView.ViewHolder, int, int, int, int)} come in one by one,
     * then start the animations together in the later call to {@link #runPendingAnimations()}.
     *
     * @param oldHolder The original item that changed.
     * @param newHolder The new item that was created with the changed content. Might be null
     * @param fromLeft  Left of the old view holder
     * @param fromTop   Top of the old view holder
     * @param toLeft    Left of the new view holder
     * @param toTop     Top of the new view holder
     * @return true if a later call to {@link #runPendingAnimations()} is requested,
     * false otherwise.
     */
    public abstract boolean animateChange(TimelineView.ViewHolder oldHolder,
                                          TimelineView.ViewHolder newHolder, int fromLeft, int fromTop, int toLeft, int toTop);

    /**
     * Method to be called by subclasses when a remove animation is done.
     *
     * @param item The item which has been removed
     * @see TimelineView.ItemAnimator#animateDisappearance(TimelineView.ViewHolder, ItemHolderInfo,
     * ItemHolderInfo)
     */
    public final void dispatchRemoveFinished(TimelineView.ViewHolder item) {
        onRemoveFinished(item);
        dispatchAnimationFinished(item);
    }

    /**
     * Method to be called by subclasses when a move animation is done.
     *
     * @param item The item which has been moved
     * @see TimelineView.ItemAnimator#animateDisappearance(TimelineView.ViewHolder, ItemHolderInfo,
     * ItemHolderInfo)
     * @see TimelineView.ItemAnimator#animatePersistence(TimelineView.ViewHolder, ItemHolderInfo, ItemHolderInfo)
     *
     * @see TimelineView.ItemAnimator#animateAppearance(TimelineView.ViewHolder, ItemHolderInfo, ItemHolderInfo)
     */
    public final void dispatchMoveFinished(TimelineView.ViewHolder item) {
        onMoveFinished(item);
        dispatchAnimationFinished(item);
    }

    /**
     * Method to be called by subclasses when an add animation is done.
     *
     * @param item The item which has been added
     */
    public final void dispatchAddFinished(TimelineView.ViewHolder item) {
        onAddFinished(item);
        dispatchAnimationFinished(item);
    }

    /**
     * Method to be called by subclasses when a change animation is done.
     *
     * @param item    The item which has been changed (this method must be called for
     *                each non-null ViewHolder passed into
     *                {@link #animateChange(TimelineView.ViewHolder, TimelineView.ViewHolder, int, int, int, int)}).
     * @param oldItem true if this is the old item that was changed, false if
     *                it is the new item that replaced the old item.
     * @see #animateChange(TimelineView.ViewHolder, TimelineView.ViewHolder, int, int, int, int)
     */
    public final void dispatchChangeFinished(TimelineView.ViewHolder item, boolean oldItem) {
        onChangeFinished(item, oldItem);
        dispatchAnimationFinished(item);
    }

    /**
     * Method to be called by subclasses when a remove animation is being started.
     *
     * @param item The item being removed
     */
    public final void dispatchRemoveStarting(TimelineView.ViewHolder item) {
        onRemoveStarting(item);
    }

    /**
     * Method to be called by subclasses when a move animation is being started.
     *
     * @param item The item being moved
     */
    public final void dispatchMoveStarting(TimelineView.ViewHolder item) {
        onMoveStarting(item);
    }

    /**
     * Method to be called by subclasses when an add animation is being started.
     *
     * @param item The item being added
     */
    public final void dispatchAddStarting(TimelineView.ViewHolder item) {
        onAddStarting(item);
    }

    /**
     * Method to be called by subclasses when a change animation is being started.
     *
     * @param item    The item which has been changed (this method must be called for
     *                each non-null ViewHolder passed into
     *                {@link #animateChange(TimelineView.ViewHolder, TimelineView.ViewHolder, int, int, int, int)}).
     * @param oldItem true if this is the old item that was changed, false if
     *                it is the new item that replaced the old item.
     */
    public final void dispatchChangeStarting(TimelineView.ViewHolder item, boolean oldItem) {
        onChangeStarting(item, oldItem);
    }

    /**
     * Called when a remove animation is being started on the given ViewHolder.
     * The default implementation does nothing. Subclasses may wish to override
     * this method to handle any ViewHolder-specific operations linked to animation
     * lifecycles.
     *
     * @param item The ViewHolder being animated.
     */
    @SuppressWarnings("UnusedParameters")
    public void onRemoveStarting(TimelineView.ViewHolder item) {
    }

    /**
     * Called when a remove animation has ended on the given ViewHolder.
     * The default implementation does nothing. Subclasses may wish to override
     * this method to handle any ViewHolder-specific operations linked to animation
     * lifecycles.
     *
     * @param item The ViewHolder being animated.
     */
    public void onRemoveFinished(TimelineView.ViewHolder item) {
    }

    /**
     * Called when an add animation is being started on the given ViewHolder.
     * The default implementation does nothing. Subclasses may wish to override
     * this method to handle any ViewHolder-specific operations linked to animation
     * lifecycles.
     *
     * @param item The ViewHolder being animated.
     */
    @SuppressWarnings("UnusedParameters")
    public void onAddStarting(TimelineView.ViewHolder item) {
    }

    /**
     * Called when an add animation has ended on the given ViewHolder.
     * The default implementation does nothing. Subclasses may wish to override
     * this method to handle any ViewHolder-specific operations linked to animation
     * lifecycles.
     *
     * @param item The ViewHolder being animated.
     */
    public void onAddFinished(TimelineView.ViewHolder item) {
    }

    /**
     * Called when a move animation is being started on the given ViewHolder.
     * The default implementation does nothing. Subclasses may wish to override
     * this method to handle any ViewHolder-specific operations linked to animation
     * lifecycles.
     *
     * @param item The ViewHolder being animated.
     */
    @SuppressWarnings("UnusedParameters")
    public void onMoveStarting(TimelineView.ViewHolder item) {
    }

    /**
     * Called when a move animation has ended on the given ViewHolder.
     * The default implementation does nothing. Subclasses may wish to override
     * this method to handle any ViewHolder-specific operations linked to animation
     * lifecycles.
     *
     * @param item The ViewHolder being animated.
     */
    public void onMoveFinished(TimelineView.ViewHolder item) {
    }

    /**
     * Called when a change animation is being started on the given ViewHolder.
     * The default implementation does nothing. Subclasses may wish to override
     * this method to handle any ViewHolder-specific operations linked to animation
     * lifecycles.
     *
     * @param item    The ViewHolder being animated.
     * @param oldItem true if this is the old item that was changed, false if
     *                it is the new item that replaced the old item.
     */
    @SuppressWarnings("UnusedParameters")
    public void onChangeStarting(TimelineView.ViewHolder item, boolean oldItem) {
    }

    /**
     * Called when a change animation has ended on the given ViewHolder.
     * The default implementation does nothing. Subclasses may wish to override
     * this method to handle any ViewHolder-specific operations linked to animation
     * lifecycles.
     *
     * @param item    The ViewHolder being animated.
     * @param oldItem true if this is the old item that was changed, false if
     *                it is the new item that replaced the old item.
     */
    public void onChangeFinished(TimelineView.ViewHolder item, boolean oldItem) {
    }
}

