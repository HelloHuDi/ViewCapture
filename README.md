<p align="center">
	<img width="72" height="72" src="art/icon.png"/>
</p>
<h3 align="center">ViewCapture</h3>
<p align="center">
<a href="https://github.com/HelloHuDi/ViewCapture/raw/master/app/release/app-release.apk" target="_blank"><img src="https://img.shields.io/badge/release-v1.0-blue.svg"></img></a>
<a href="https://github.com/HelloHuDi/ViewCapture/raw/master/app/release/app-release.apk" target="_blank"><img src="https://img.shields.io/badge/demo-v1.0-blue.svg"></img></a>
</p>

## support common view,ScrollView,HorizontalScrollView,ListView,RecyclerView,WebView screenshots

## RecyclerView currently only support LinearLayoutManager.VERTICAL

## dependencies :

```
implementation 'com.hd.viewcapture:viewcapture:1.0'
```

## code :

### save to file

```
ViewCapture.with(v).asJPG().save();
```

### only get bitmap

```
ViewCapture.with(v).getBitmap();
```

## screenshots:

<img src="art/hscrollview.gif" width="300px" height="500px"/> <img src="art/listview.gif" width="300px" height="500px"/> 

<img src="art/recyclerview.gif" width="300px" height="500px"/> <img src="art/scrollview.gif" width="300px" height="500px"/> 

<img src="art/view.gif" width="300px" height="500px"/> <img src="art/webview.gif" width="300px" height="500px"/>