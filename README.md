# StateRevi

StateRevi is a ``` RecyclerView ``` replacement which contains states based on its current content. If it's adapter does not have a list yet, a loading view is shown instead and if adapter's list is empty, a view with image and text (or anything you want). Migration from ``` RecyclerView ``` to ``` StateRevi ``` is literally 2 words (and removing some boilerplate probably). Replace your ```RecyclerView``` with ```StateRevi```, extend your adapter to ```StateReviAdapter```, pass the type your list will use and you are good to go.

![Preview](/image/preview.gif)

## Implementaton
1. On root build.gradle
```sh
allprojects {
   repositories { 
       ...
       maven { url 'https://jitpack.io' }
   }
}
```
2. On app's gradle
```sh
dependencies {
    implementation 'com.github.gpallas16:StateRevi:$latest_ver'
}
```

## Usage
For a full demo of this project see the sample/ folder.

1. StateRevi
    Create a StateRevi referene as you would do on any view and add your text and icon you want to show when the list is empty.
    * Xml
        ```sh 
        <com.github.gpallas16.staterevi.StateRevi
        ...
        app:emptyCaption="No data yet."
        app:emptyIcon="@drawable/ic_image"
        />
    * Java
        ```sh
        StateRevi stateRevi = new StateRevi(context);
        stateRevi.setEmptyCaption("No data yet.");
        stateRevi.setEmptyIcon(ContextCompat.getDrawable(context, R.drawable.ic_image));
        
2.  StateReviAdapter
    Make anything you would do on a RecyclerView.Adapter with the only difference that you pass your list's item as a parameter too. 
    ```sh
    public class SampleAdapter extends StateAdapter<YourListItemModel, YourViewHolder> {
        ...
    }
    ```
     > Note: You don't have to hold a reference to your list, as it's internally stored and can be accessed with getList() method.
3. Bind and populate your data to the adapter
    ```sh
    stateRevi.setAdapter(sampleAdapter);
    sampleAdapter.setList(yourItemList);
    ```

## Customization
* Use custom loading and empty view if you don't want to use the default ones.
    * Custom loading view
        ```sh
        stateRevi.setLoadingStateView(
                new LoadingStateView(
                        //the layout you want to use
                        R.layout.view_custom_loading 
                )
        );
        ```
    * Custom empty view
        ```sh
        stateRevi.setEmptyStateView(
                new EmptyStateView(
                        //the layout you want to use
                        R.layout.view_custom_empty,
                        /* the id of the TextView you want the text to be shown
                           use EmptyStateView.UNDEFINED_ID if you don't want a text */
                        R.id.customCaption,
                        /* the id of the ImageView you want the icon to be shown
                           use EmptyStateView.UNDEFINED_ID if you don't want an icon */
                        R.id.customImage 
                )
        );
        ```
    > Note: For better perfomance set your views before setting an adapter. Doing otherwise will cause a rerender.

* Default icon, text and views.
Too lazy to write everytime your empty captions and icons or setting up you views? Yeah, me too. Just use the global setter and all your StateRevies will default to those.
    ```sh
    StateReviResHandler.setGlobalDefaultCaption("Global caption");
    StateReviResHandler.setGlobalDefaultIcon(R.drawable.ic_global);
    StateReviResHandler.setGlobalCustomLoadingView(customLoadingView);
    StateReviResHandler.setGlobalCustomEmptyView(customEmptyView);
    ```

# Handling State
StateRevi contains three states
* State.LOADING
* State.EMPTY
* State.DATA

StateRevi by default starts with ```State.LOADING```,  showing the loading view. Once the adapter.setList(list) runs, the state changes to either ``` State.EMPTY ``` or ``` State.DATA```, depending on the list being empty or not.
If you want to show the loading view again (i.e. swipe up refresh) you can set the ```State.LOADING``` manually via ```stateRevi.setState(StateRevi.State.LOADING)``` or by setting a null list to the adapter.


# DiffUtil
All sounds good but if the setList() is final how do you apply a DiffUtil? All pretty simple here too.
Juse override function getDiffResult in you adapter and you are ready.
```sh
public class DiffUtilReviAdapter extends StateReviAdapter<YourListItemModel, YourViewHolder> {
    ...
    
    @Override
    protected DiffUtil.DiffResult getDiffResult(List<YourListItemModel> oldList, List<YourListItemModel> newList) {
        return DiffUtil.calculateDiff(new YouDiffUtilCallback(oldList, newList));
    }

}
```

# Public methods 
## StateRevi
| Method | Usage |
| ------ | ------ |
| setAdapter(@Nullable Adapter adapter) | Attaches adapter to StateRevi. |
| setState(@State int state) | Sets a StateRevi.State to StateRevi. |
| setLoadingStateView(LoadingStateView loadingStateView) | Sets a custom loading view for this StateRevi instance. Overrides the global loading view. |
| setEmptyStateView(EmptyStateView emptyStateView) |  Sets a custom empty view for this StateRevi instance. Overrides the global empty view. |
| setEmptyCaption(String caption) | Equivalent to xml app:emptyCaption. Sets the text to be used when empty view is showing. |
| setEmptyIcon(Drawable icon) | Equivalent to xml app:emptyIcon. Sets the icon to be used when empty view is showing. |

## StateReviAdapter
| Method | Usage |
| ------ | ------ |
| setList(@Nullable List<T> items) | Sets a list to adapter. |
| List<T> getList() | Returns the current list showing. |
| DiffUtil.DiffResult getDiffResult(List<T> oldList, List<T> newList) | Override to add DiffUtil functionality. |

## StateReviResHandler
| Method | Usage |
| ------ | ------ |
| setGlobalCustomLoadingView(LoadingStateView customLoadingView) | Sets a custom loading view to be used as a default on any StateRevi instance. |
| setGlobalCustomEmptyView(EmptyStateView customEmptyView) | Sets a custom empty view to be used as a default on any StateRevi instance. |
| setGlobalDefaultIcon(int icon) | Sets an icon to be used as a default on any StateRevi instance. |
| setGlobalDefaultCaption(int icon) | Sets an text to be used as a default on any StateRevi instance. |

# License
```sh
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

