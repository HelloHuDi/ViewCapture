<p align="center">
	<img width="72" height="72" src="art/icon.png"/>
</p>
<h3 align="center">ViewCapture</h3>
<p align="center">
<a href="https://github.com/HelloHuDi/ViewCapture/raw/master/app/release/app-release.apk" target="_blank"><img src="https://img.shields.io/badge/release-v1.4-blue.svg"></img></a>
<a href="https://github.com/HelloHuDi/ViewCapture/raw/master/app/release/app-release.apk" target="_blank"><img src="https://img.shields.io/badge/demo-v1.4-blue.svg"></img></a>
</p>

## provided View, ScrollView, HorizontalScrollView, ListView, RecyclerView, WebView screenshots.

### RecyclerView currently only support LinearLayoutManager.VERTICAL

### support android sdk version 19+

## dependencies :

```
implementation 'com.hd.viewcapture:viewcapture:1.4'
```

## code :

### save to file

```
ViewCapture.with(v).asJPG().setOnSaveResultListener(this).save();
```

### only get bitmap

```
ViewCapture.with(v).getBitmap();
```

## screenshots:

<img src="art/hscrollview.gif" width="280px" height="500px"/> <img src="art/listview.gif" width="280px" height="500px"/> <img src="art/recyclerview.gif" width="280px" height="500px"/> 

<img src="art/scrollview.gif" width="280px" height="500px"/> <img src="art/view.gif" width="280px" height="500px"/> <img src="art/webview.gif" width="280px" height="500px"/>


### License

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
