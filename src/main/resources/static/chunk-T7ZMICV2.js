import{C as Jt,e as Bn,o as zn,v as Vn}from"./chunk-LKMXNO7X.js";import{$ as wt,A as tt,Aa as ke,Ac as qt,B as Vt,C as wn,Ca as Kt,Cb as mt,E as jt,Ea as kn,Eb as xt,F as Ht,Fa as Zt,Ga as Rn,H as yt,I as En,Lc as Pn,M as xn,O as Ut,Oc as Fn,P as Wt,Pb as Ct,Qb as St,R as et,Rb as X,S as Cn,T as ct,Ua as q,Uc as Nn,W as Sn,X as h,Xc as y,Y as C,Yc as Ln,_ as p,_a as Dn,_c as Qt,a as P,aa as a,bc as $,c as vt,cc as N,d as mn,db as nt,e as f,ea as dt,eb as $t,ec as On,f as pn,fc as An,gb as j,gc as Mn,h as fn,hb as ht,i as bn,ka as S,kb as Et,la as b,lb as Re,lc as Tn,m as W,ma as In,mc as L,n as _n,oa as F,ob as O,oc as Gt,pa as _,pb as I,q as V,qa as Yt,qb as k,r as gn,sa as Z,tb as Y,u as vn,va as Xt,w as yn,wa as ut,xa as Ie,xb as De,za as v}from"./chunk-ENKTIU2L.js";import{a as x,b as hn}from"./chunk-GYWX2ZNF.js";var Oe;try{Oe=typeof Intl<"u"&&Intl.v8BreakIterator}catch(i){Oe=!1}var g=(()=>{class i{_platformId=a(kn);isBrowser=this._platformId?zn(this._platformId):typeof document=="object"&&!!document;EDGE=this.isBrowser&&/(edge)/i.test(navigator.userAgent);TRIDENT=this.isBrowser&&/(msie|trident)/i.test(navigator.userAgent);BLINK=this.isBrowser&&!!(window.chrome||Oe)&&typeof CSS<"u"&&!this.EDGE&&!this.TRIDENT;WEBKIT=this.isBrowser&&/AppleWebKit/i.test(navigator.userAgent)&&!this.BLINK&&!this.EDGE&&!this.TRIDENT;IOS=this.isBrowser&&/iPad|iPhone|iPod/.test(navigator.userAgent)&&!("MSStream"in window);FIREFOX=this.isBrowser&&/(firefox|minefield)/i.test(navigator.userAgent);ANDROID=this.isBrowser&&/android/i.test(navigator.userAgent)&&!this.TRIDENT;SAFARI=this.isBrowser&&/safari/i.test(navigator.userAgent)&&this.WEBKIT;constructor(){}static \u0275fac=function(e){return new(e||i)};static \u0275prov=h({token:i,factory:i.\u0275fac,providedIn:"root"})}return i})();function It(i,n=0){return jn(i)?Number(i):arguments.length===2?n:0}function jn(i){return!isNaN(parseFloat(i))&&!isNaN(Number(i))}function K(i){return i instanceof v?i.nativeElement:i}var eo=new p("cdk-dir-doc",{providedIn:"root",factory:()=>a(b)}),no=/^(ar|ckb|dv|he|iw|fa|nqo|ps|sd|ug|ur|yi|.*[-_](Adlm|Arab|Hebr|Nkoo|Rohg|Thaa))(?!.*[-_](Latn|Cyrl)($|-|_))($|-|_)/i;function Hn(i){let n=i?.toLowerCase()||"";return n==="auto"&&typeof navigator<"u"&&navigator?.language?no.test(navigator.language)?"rtl":"ltr":n==="rtl"?"rtl":"ltr"}var pt=(()=>{class i{get value(){return this.valueSignal()}valueSignal=Z("ltr");change=new F;constructor(){let t=a(eo,{optional:!0});if(t){let e=t.body?t.body.dir:null,o=t.documentElement?t.documentElement.dir:null;this.valueSignal.set(Hn(e||o||"ltr"))}}ngOnDestroy(){this.change.complete()}static \u0275fac=function(e){return new(e||i)};static \u0275prov=h({token:i,factory:i.\u0275fac,providedIn:"root"})}return i})();var H=(function(i){return i[i.NORMAL=0]="NORMAL",i[i.NEGATED=1]="NEGATED",i[i.INVERTED=2]="INVERTED",i})(H||{}),te,it;function ee(){if(it==null){if(typeof document!="object"||!document||typeof Element!="function"||!Element)return it=!1,it;if(document.documentElement?.style&&"scrollBehavior"in document.documentElement.style)it=!0;else{let i=Element.prototype.scrollTo;i?it=!/\{\s*\[native code\]\s*\}/.test(i.toString()):it=!1}}return it}function ft(){if(typeof document!="object"||!document)return H.NORMAL;if(te==null){let i=document.createElement("div"),n=i.style;i.dir="rtl",n.width="1px",n.overflow="auto",n.visibility="hidden",n.pointerEvents="none",n.position="absolute";let t=document.createElement("div"),e=t.style;e.width="2px",e.height="1px",i.appendChild(t),document.body.appendChild(i),te=H.NORMAL,i.scrollLeft===0&&(i.scrollLeft=1,te=i.scrollLeft===0?H.NEGATED:H.INVERTED),i.remove()}return te}var Un=class{};function xr(i){return i&&typeof i.connect=="function"&&!(i instanceof mn)}var kt=(function(i){return i[i.REPLACED=0]="REPLACED",i[i.INSERTED=1]="INSERTED",i[i.MOVED=2]="MOVED",i[i.REMOVED=3]="REMOVED",i})(kt||{}),Wn=class{viewCacheSize=20;_viewCache=[];applyChanges(n,t,e,o,r){n.forEachOperation((s,l,c)=>{let u,d;if(s.previousIndex==null){let m=()=>e(s,l,c);u=this._insertView(m,c,t,o(s)),d=u?kt.INSERTED:kt.REPLACED}else c==null?(this._detachAndCacheView(l,t),d=kt.REMOVED):(u=this._moveView(l,c,t,o(s)),d=kt.MOVED);r&&r({context:u?.context,operation:d,record:s})})}detach(){for(let n of this._viewCache)n.destroy();this._viewCache=[]}_insertView(n,t,e,o){let r=this._insertViewFromCache(t,e);if(r){r.context.$implicit=o;return}let s=n();return e.createEmbeddedView(s.templateRef,s.context,s.index)}_detachAndCacheView(n,t){let e=t.detach(n);this._maybeCacheView(e,t)}_moveView(n,t,e,o){let r=e.get(n);return e.move(r,t),r.context.$implicit=o,r}_maybeCacheView(n,t){if(this._viewCache.length<this.viewCacheSize)this._viewCache.push(n);else{let e=t.indexOf(n);e===-1?n.destroy():t.remove(e)}}_insertViewFromCache(n,t){let e=this._viewCache.pop();return e&&t.insert(e,n),e||null}};var U=(()=>{class i{static \u0275fac=function(e){return new(e||i)};static \u0275mod=I({type:i});static \u0275inj=C({})}return i})();var io=["contentWrapper"],oo=["*"],Xn=new p("VIRTUAL_SCROLL_STRATEGY"),Ae=class{_scrolledIndexChange=new f;scrolledIndexChange=this._scrolledIndexChange.pipe(yt());_viewport=null;_itemSize;_minBufferPx;_maxBufferPx;constructor(n,t,e){this._itemSize=n,this._minBufferPx=t,this._maxBufferPx=e}attach(n){this._viewport=n,this._updateTotalContentSize(),this._updateRenderedRange()}detach(){this._scrolledIndexChange.complete(),this._viewport=null}updateItemAndBufferSize(n,t,e){e<t,this._itemSize=n,this._minBufferPx=t,this._maxBufferPx=e,this._updateTotalContentSize(),this._updateRenderedRange()}onContentScrolled(){this._updateRenderedRange()}onDataLengthChanged(){this._updateTotalContentSize(),this._updateRenderedRange()}onContentRendered(){}onRenderedOffsetChanged(){}scrollToIndex(n,t){this._viewport&&this._viewport.scrollToOffset(n*this._itemSize,t)}_updateTotalContentSize(){this._viewport&&this._viewport.setTotalContentSize(this._viewport.getDataLength()*this._itemSize)}_updateRenderedRange(){if(!this._viewport)return;let n=this._viewport.getRenderedRange(),t={start:n.start,end:n.end},e=this._viewport.getViewportSize(),o=this._viewport.getDataLength(),r=this._viewport.measureScrollOffset(),s=this._itemSize>0?r/this._itemSize:0;if(t.end>o){let c=Math.ceil(e/this._itemSize),u=Math.max(0,Math.min(s,o-c));s!=u&&(s=u,r=u*this._itemSize,t.start=Math.floor(s)),t.end=Math.max(0,Math.min(o,t.start+c))}let l=r-t.start*this._itemSize;if(l<this._minBufferPx&&t.start!=0){let c=Math.ceil((this._maxBufferPx-l)/this._itemSize);t.start=Math.max(0,t.start-c),t.end=Math.min(o,Math.ceil(s+(e+this._minBufferPx)/this._itemSize))}else{let c=t.end*this._itemSize-(r+e);if(c<this._minBufferPx&&t.end!=o){let u=Math.ceil((this._maxBufferPx-c)/this._itemSize);u>0&&(t.end=Math.min(o,t.end+u),t.start=Math.max(0,Math.floor(s-this._minBufferPx/this._itemSize)))}}this._viewport.setRenderedRange(t),this._viewport.setRenderedContentOffset(Math.round(this._itemSize*t.start)),this._scrolledIndexChange.next(Math.floor(s))}};function ro(i){return i._scrollStrategy}var so=(()=>{class i{get itemSize(){return this._itemSize}set itemSize(t){this._itemSize=It(t)}_itemSize=20;get minBufferPx(){return this._minBufferPx}set minBufferPx(t){this._minBufferPx=It(t)}_minBufferPx=100;get maxBufferPx(){return this._maxBufferPx}set maxBufferPx(t){this._maxBufferPx=It(t)}_maxBufferPx=200;_scrollStrategy=new Ae(this.itemSize,this.minBufferPx,this.maxBufferPx);ngOnChanges(){this._scrollStrategy.updateItemAndBufferSize(this.itemSize,this.minBufferPx,this.maxBufferPx)}static \u0275fac=function(e){return new(e||i)};static \u0275dir=k({type:i,selectors:[["cdk-virtual-scroll-viewport","itemSize",""]],inputs:{itemSize:"itemSize",minBufferPx:"minBufferPx",maxBufferPx:"maxBufferPx"},features:[qt([{provide:Xn,useFactory:ro,deps:[Sn(()=>i)]}]),ut]})}return i})(),ao=20,Rt=(()=>{class i{_ngZone=a(_);_platform=a(g);_renderer=a(j).createRenderer(null,null);_cleanupGlobalListener;constructor(){}_scrolled=new f;_scrolledCount=0;scrollContainers=new Map;register(t){this.scrollContainers.has(t)||this.scrollContainers.set(t,t.elementScrolled().subscribe(()=>this._scrolled.next(t)))}deregister(t){let e=this.scrollContainers.get(t);e&&(e.unsubscribe(),this.scrollContainers.delete(t))}scrolled(t=ao){return this._platform.isBrowser?new vt(e=>{this._cleanupGlobalListener||(this._cleanupGlobalListener=this._ngZone.runOutsideAngular(()=>this._renderer.listen("document","scroll",()=>this._scrolled.next())));let o=t>0?this._scrolled.pipe(Vt(t)).subscribe(e):this._scrolled.subscribe(e);return this._scrolledCount++,()=>{o.unsubscribe(),this._scrolledCount--,this._scrolledCount||(this._cleanupGlobalListener?.(),this._cleanupGlobalListener=void 0)}}):W()}ngOnDestroy(){this._cleanupGlobalListener?.(),this._cleanupGlobalListener=void 0,this.scrollContainers.forEach((t,e)=>this.deregister(e)),this._scrolled.complete()}ancestorScrolled(t,e){let o=this.getAncestorScrollContainers(t);return this.scrolled(e).pipe(tt(r=>!r||o.indexOf(r)>-1))}getAncestorScrollContainers(t){let e=[];return this.scrollContainers.forEach((o,r)=>{this._scrollableContainsElement(r,t)&&e.push(r)}),e}_scrollableContainsElement(t,e){let o=K(e),r=t.getElementRef().nativeElement;do if(o==r)return!0;while(o=o.parentElement);return!1}static \u0275fac=function(e){return new(e||i)};static \u0275prov=h({token:i,factory:i.\u0275fac,providedIn:"root"})}return i})(),Te=(()=>{class i{elementRef=a(v);scrollDispatcher=a(Rt);ngZone=a(_);dir=a(pt,{optional:!0});_scrollElement=this.elementRef.nativeElement;_destroyed=new f;_renderer=a(ht);_cleanupScroll;_elementScrolled=new f;constructor(){}ngOnInit(){this._cleanupScroll=this.ngZone.runOutsideAngular(()=>this._renderer.listen(this._scrollElement,"scroll",t=>this._elementScrolled.next(t))),this.scrollDispatcher.register(this)}ngOnDestroy(){this._cleanupScroll?.(),this._elementScrolled.complete(),this.scrollDispatcher.deregister(this),this._destroyed.next(),this._destroyed.complete()}elementScrolled(){return this._elementScrolled}getElementRef(){return this.elementRef}scrollTo(t){let e=this.elementRef.nativeElement,o=this.dir&&this.dir.value=="rtl";t.left==null&&(t.left=o?t.end:t.start),t.right==null&&(t.right=o?t.start:t.end),t.bottom!=null&&(t.top=e.scrollHeight-e.clientHeight-t.bottom),o&&ft()!=H.NORMAL?(t.left!=null&&(t.right=e.scrollWidth-e.clientWidth-t.left),ft()==H.INVERTED?t.left=t.right:ft()==H.NEGATED&&(t.left=t.right?-t.right:t.right)):t.right!=null&&(t.left=e.scrollWidth-e.clientWidth-t.right),this._applyScrollToOptions(t)}_applyScrollToOptions(t){let e=this.elementRef.nativeElement;ee()?e.scrollTo(t):(t.top!=null&&(e.scrollTop=t.top),t.left!=null&&(e.scrollLeft=t.left))}measureScrollOffset(t){let e="left",o="right",r=this.elementRef.nativeElement;if(t=="top")return r.scrollTop;if(t=="bottom")return r.scrollHeight-r.clientHeight-r.scrollTop;let s=this.dir&&this.dir.value=="rtl";return t=="start"?t=s?o:e:t=="end"&&(t=s?e:o),s&&ft()==H.INVERTED?t==e?r.scrollWidth-r.clientWidth-r.scrollLeft:r.scrollLeft:s&&ft()==H.NEGATED?t==e?r.scrollLeft+r.scrollWidth-r.clientWidth:-r.scrollLeft:t==e?r.scrollLeft:r.scrollWidth-r.clientWidth-r.scrollLeft}static \u0275fac=function(e){return new(e||i)};static \u0275dir=k({type:i,selectors:[["","cdk-scrollable",""],["","cdkScrollable",""]]})}return i})(),lo=20,ot=(()=>{class i{_platform=a(g);_listeners;_viewportSize=null;_change=new f;_document=a(b);constructor(){let t=a(_),e=a(j).createRenderer(null,null);t.runOutsideAngular(()=>{if(this._platform.isBrowser){let o=r=>this._change.next(r);this._listeners=[e.listen("window","resize",o),e.listen("window","orientationchange",o)]}this.change().subscribe(()=>this._viewportSize=null)})}ngOnDestroy(){this._listeners?.forEach(t=>t()),this._change.complete()}getViewportSize(){this._viewportSize||this._updateViewportSize();let t={width:this._viewportSize.width,height:this._viewportSize.height};return this._platform.isBrowser||(this._viewportSize=null),t}getViewportRect(){let t=this.getViewportScrollPosition(),{width:e,height:o}=this.getViewportSize();return{top:t.top,left:t.left,bottom:t.top+o,right:t.left+e,height:o,width:e}}getViewportScrollPosition(){if(!this._platform.isBrowser)return{top:0,left:0};let t=this._document,e=this._getWindow(),o=t.documentElement,r=o.getBoundingClientRect(),s=-r.top||t.body?.scrollTop||e.scrollY||o.scrollTop||0,l=-r.left||t.body?.scrollLeft||e.scrollX||o.scrollLeft||0;return{top:s,left:l}}change(t=lo){return t>0?this._change.pipe(Vt(t)):this._change}_getWindow(){return this._document.defaultView||window}_updateViewportSize(){let t=this._getWindow();this._viewportSize=this._platform.isBrowser?{width:t.innerWidth,height:t.innerHeight}:{width:0,height:0}}static \u0275fac=function(e){return new(e||i)};static \u0275prov=h({token:i,factory:i.\u0275fac,providedIn:"root"})}return i})(),Yn=new p("VIRTUAL_SCROLLABLE"),co=(()=>{class i extends Te{constructor(){super()}measureViewportSize(t){let e=this.elementRef.nativeElement;return t==="horizontal"?e.clientWidth:e.clientHeight}static \u0275fac=function(e){return new(e||i)};static \u0275dir=k({type:i,features:[Y]})}return i})();function uo(i,n){return i.start==n.start&&i.end==n.end}var ho=typeof requestAnimationFrame<"u"?bn:fn,mo=new p("CDK_VIRTUAL_SCROLL_VIEWPORT"),po=(()=>{class i extends co{elementRef=a(v);_changeDetectorRef=a(Nn);_scrollStrategy=a(Xn,{optional:!0});scrollable=a(Yn,{optional:!0});_platform=a(g);_detachedSubject=new f;_renderedRangeSubject=new f;_renderedContentOffsetSubject=new f;get orientation(){return this._orientation}set orientation(t){this._orientation!==t&&(this._orientation=t,this._calculateSpacerSize())}_orientation="vertical";appendOnly=!1;scrolledIndexChange=new vt(t=>this._scrollStrategy.scrolledIndexChange.subscribe(e=>Promise.resolve().then(()=>this.ngZone.run(()=>t.next(e)))));_contentWrapper;renderedRangeStream=this._renderedRangeSubject;renderedContentOffset=this._renderedContentOffsetSubject.pipe(tt(t=>t!==null),yt());_totalContentSize=0;_totalContentWidth=Z("");_totalContentHeight=Z("");_renderedContentTransform;_renderedRange={start:0,end:0};_dataLength=0;_viewportSize=0;_forOf=null;_renderedContentOffset=0;_renderedContentOffsetNeedsRewrite=!1;_changeDetectionNeeded=Z(!1);_runAfterChangeDetection=[];_viewportChanges=P.EMPTY;_injector=a(S);_isDestroyed=!1;constructor(){super();let t=a(ot);this._scrollStrategy,this._viewportChanges=t.change().subscribe(()=>{this.checkViewportSize()}),this.scrollable||(this.elementRef.nativeElement.classList.add("cdk-virtual-scrollable"),this.scrollable=this);let e=Xt(()=>{this._changeDetectionNeeded()&&this._doChangeDetection()},{injector:a(mt).injector});a(In).onDestroy(()=>{e.destroy()})}ngOnInit(){this._platform.isBrowser&&(this.scrollable===this&&super.ngOnInit(),this.ngZone.runOutsideAngular(()=>Promise.resolve().then(()=>{this._measureViewportSize(),this._scrollStrategy.attach(this),this.scrollable.elementScrolled().pipe(Wt(null),Vt(0,ho),et(this._destroyed)).subscribe(()=>this._scrollStrategy.onContentScrolled()),this._markChangeDetectionNeeded()})))}ngOnDestroy(){this.detach(),this._scrollStrategy.detach(),this._renderedRangeSubject.complete(),this._detachedSubject.complete(),this._viewportChanges.unsubscribe(),this._isDestroyed=!0,super.ngOnDestroy()}attach(t){this._forOf,this.ngZone.runOutsideAngular(()=>{this._forOf=t,this._forOf.dataStream.pipe(et(this._detachedSubject)).subscribe(e=>{let o=e.length;o!==this._dataLength&&(this._dataLength=o,this._scrollStrategy.onDataLengthChanged()),this._doChangeDetection()})})}detach(){this._forOf=null,this._detachedSubject.next()}getDataLength(){return this._dataLength}getViewportSize(){return this._viewportSize}getRenderedRange(){return this._renderedRange}measureBoundingClientRectWithScrollOffset(t){return this.getElementRef().nativeElement.getBoundingClientRect()[t]}setTotalContentSize(t){this._totalContentSize!==t&&(this._totalContentSize=t,this._calculateSpacerSize(),this._markChangeDetectionNeeded())}setRenderedRange(t){uo(this._renderedRange,t)||(this.appendOnly&&(t={start:0,end:Math.max(this._renderedRange.end,t.end)}),this._renderedRangeSubject.next(this._renderedRange=t),this._markChangeDetectionNeeded(()=>this._scrollStrategy.onContentRendered()))}getOffsetToRenderedContentStart(){return this._renderedContentOffsetNeedsRewrite?null:this._renderedContentOffset}setRenderedContentOffset(t,e="to-start"){t=this.appendOnly&&e==="to-start"?0:t;let o=this.dir&&this.dir.value=="rtl",r=this.orientation=="horizontal",s=r?"X":"Y",c=`translate${s}(${Number((r&&o?-1:1)*t)}px)`;this._renderedContentOffset=t,e==="to-end"&&(c+=` translate${s}(-100%)`,this._renderedContentOffsetNeedsRewrite=!0),this._renderedContentTransform!=c&&(this._renderedContentTransform=c,this._markChangeDetectionNeeded(()=>{this._renderedContentOffsetNeedsRewrite?(this._renderedContentOffset-=this.measureRenderedContentSize(),this._renderedContentOffsetNeedsRewrite=!1,this.setRenderedContentOffset(this._renderedContentOffset)):this._scrollStrategy.onRenderedOffsetChanged()}))}scrollToOffset(t,e="auto"){let o={behavior:e};this.orientation==="horizontal"?o.start=t:o.top=t,this.scrollable.scrollTo(o)}scrollToIndex(t,e="auto"){this._scrollStrategy.scrollToIndex(t,e)}measureScrollOffset(t){let e;return this.scrollable==this?e=o=>super.measureScrollOffset(o):e=o=>this.scrollable.measureScrollOffset(o),Math.max(0,e(t??(this.orientation==="horizontal"?"start":"top"))-this.measureViewportOffset())}measureViewportOffset(t){let e,o="left",r="right",s=this.dir?.value=="rtl";t=="start"?e=s?r:o:t=="end"?e=s?o:r:t?e=t:e=this.orientation==="horizontal"?"left":"top";let l=this.scrollable.measureBoundingClientRectWithScrollOffset(e);return this.elementRef.nativeElement.getBoundingClientRect()[e]-l}measureRenderedContentSize(){let t=this._contentWrapper.nativeElement;return this.orientation==="horizontal"?t.offsetWidth:t.offsetHeight}measureRangeSize(t){return this._forOf?this._forOf.measureRangeSize(t,this.orientation):0}checkViewportSize(){this._measureViewportSize(),this._scrollStrategy.onDataLengthChanged()}_measureViewportSize(){this._viewportSize=this.scrollable.measureViewportSize(this.orientation)}_markChangeDetectionNeeded(t){t&&this._runAfterChangeDetection.push(t),!Pn(this._changeDetectionNeeded)&&this.ngZone.runOutsideAngular(()=>{Promise.resolve().then(()=>{this.ngZone.run(()=>{this._changeDetectionNeeded.set(!0)})})})}_doChangeDetection(){this._isDestroyed||this.ngZone.run(()=>{this._changeDetectorRef.markForCheck(),this._contentWrapper.nativeElement.style.transform=this._renderedContentTransform,this._renderedContentOffsetSubject.next(this.getOffsetToRenderedContentStart()),nt(()=>{this._changeDetectionNeeded.set(!1);let t=this._runAfterChangeDetection;this._runAfterChangeDetection=[];for(let e of t)e()},{injector:this._injector})})}_calculateSpacerSize(){this._totalContentHeight.set(this.orientation==="horizontal"?"":`${this._totalContentSize}px`),this._totalContentWidth.set(this.orientation==="horizontal"?`${this._totalContentSize}px`:"")}static \u0275fac=function(e){return new(e||i)};static \u0275cmp=O({type:i,selectors:[["cdk-virtual-scroll-viewport"]],viewQuery:function(e,o){if(e&1&&On(io,7),e&2){let r;An(r=Mn())&&(o._contentWrapper=r.first)}},hostAttrs:[1,"cdk-virtual-scroll-viewport"],hostVars:4,hostBindings:function(e,o){e&2&&L("cdk-virtual-scroll-orientation-horizontal",o.orientation==="horizontal")("cdk-virtual-scroll-orientation-vertical",o.orientation!=="horizontal")},inputs:{orientation:"orientation",appendOnly:[2,"appendOnly","appendOnly",y]},outputs:{scrolledIndexChange:"scrolledIndexChange"},features:[qt([{provide:Te,useFactory:()=>a(Yn,{optional:!0})||a(i)},{provide:mo,useExisting:i}]),Y],ngContentSelectors:oo,decls:4,vars:4,consts:[["contentWrapper",""],[1,"cdk-virtual-scroll-content-wrapper"],[1,"cdk-virtual-scroll-spacer"]],template:function(e,o){e&1&&($(),Ct(0,"div",1,0),N(2),St(),X(3,"div",2)),e&2&&(Dn(3),Tn("width",o._totalContentWidth())("height",o._totalContentHeight()))},styles:[`cdk-virtual-scroll-viewport {
  display: block;
  position: relative;
  transform: translateZ(0);
}

.cdk-virtual-scrollable {
  overflow: auto;
  will-change: scroll-position;
  contain: strict;
}

.cdk-virtual-scroll-content-wrapper {
  position: absolute;
  top: 0;
  left: 0;
  contain: content;
}
[dir=rtl] .cdk-virtual-scroll-content-wrapper {
  right: 0;
  left: auto;
}

.cdk-virtual-scroll-orientation-horizontal .cdk-virtual-scroll-content-wrapper {
  min-height: 100%;
}
.cdk-virtual-scroll-orientation-horizontal .cdk-virtual-scroll-content-wrapper > dl:not([cdkVirtualFor]), .cdk-virtual-scroll-orientation-horizontal .cdk-virtual-scroll-content-wrapper > ol:not([cdkVirtualFor]), .cdk-virtual-scroll-orientation-horizontal .cdk-virtual-scroll-content-wrapper > table:not([cdkVirtualFor]), .cdk-virtual-scroll-orientation-horizontal .cdk-virtual-scroll-content-wrapper > ul:not([cdkVirtualFor]) {
  padding-left: 0;
  padding-right: 0;
  margin-left: 0;
  margin-right: 0;
  border-left-width: 0;
  border-right-width: 0;
  outline: none;
}

.cdk-virtual-scroll-orientation-vertical .cdk-virtual-scroll-content-wrapper {
  min-width: 100%;
}
.cdk-virtual-scroll-orientation-vertical .cdk-virtual-scroll-content-wrapper > dl:not([cdkVirtualFor]), .cdk-virtual-scroll-orientation-vertical .cdk-virtual-scroll-content-wrapper > ol:not([cdkVirtualFor]), .cdk-virtual-scroll-orientation-vertical .cdk-virtual-scroll-content-wrapper > table:not([cdkVirtualFor]), .cdk-virtual-scroll-orientation-vertical .cdk-virtual-scroll-content-wrapper > ul:not([cdkVirtualFor]) {
  padding-top: 0;
  padding-bottom: 0;
  margin-top: 0;
  margin-bottom: 0;
  border-top-width: 0;
  border-bottom-width: 0;
  outline: none;
}

.cdk-virtual-scroll-spacer {
  height: 1px;
  transform-origin: 0 0;
  flex: 0 0 auto;
}
[dir=rtl] .cdk-virtual-scroll-spacer {
  transform-origin: 100% 0;
}
`],encapsulation:2,changeDetection:0})}return i})();var Me=(()=>{class i{static \u0275fac=function(e){return new(e||i)};static \u0275mod=I({type:i});static \u0275inj=C({})}return i})(),Pe=(()=>{class i{static \u0275fac=function(e){return new(e||i)};static \u0275mod=I({type:i});static \u0275inj=C({imports:[U,Me,U,Me]})}return i})();var ne=new WeakMap,B=(()=>{class i{_appRef;_injector=a(S);_environmentInjector=a(dt);load(t){let e=this._appRef=this._appRef||this._injector.get(mt),o=ne.get(e);o||(o={loaders:new Set,refs:[]},ne.set(e,o),e.onDestroy(()=>{ne.get(e)?.refs.forEach(r=>r.destroy()),ne.delete(e)})),o.loaders.has(t)||(o.loaders.add(t),o.refs.push(Qt(t,{environmentInjector:this._environmentInjector})))}static \u0275fac=function(e){return new(e||i)};static \u0275prov=h({token:i,factory:i.\u0275fac,providedIn:"root"})}return i})();var oe=(()=>{class i{static \u0275fac=function(e){return new(e||i)};static \u0275cmp=O({type:i,selectors:[["ng-component"]],exportAs:["cdkVisuallyHidden"],decls:0,vars:0,template:function(e,o){},styles:[`.cdk-visually-hidden {
  border: 0;
  clip: rect(0 0 0 0);
  height: 1px;
  margin: -1px;
  overflow: hidden;
  padding: 0;
  position: absolute;
  width: 1px;
  white-space: nowrap;
  outline: 0;
  -webkit-appearance: none;
  -moz-appearance: none;
  left: 0;
}
[dir=rtl] .cdk-visually-hidden {
  left: auto;
  right: 0;
}
`],encapsulation:2,changeDetection:0})}return i})(),ie;function fo(){if(ie===void 0&&(ie=null,typeof window<"u")){let i=window;i.trustedTypes!==void 0&&(ie=i.trustedTypes.createPolicy("angular#components",{createHTML:n=>n}))}return ie}function rt(i){return fo()?.createHTML(i)||i}function Kn(i,n,t){let e=t.sanitize(q.HTML,n);i.innerHTML=rt(e||"")}function Zn(i){return Error(`Unable to find icon with the name "${i}"`)}function bo(){return Error("Could not find HttpClient for use with Angular Material icons. Please add provideHttpClient() to your providers.")}function $n(i){return Error(`The URL provided to MatIconRegistry was not trusted as a resource URL via Angular's DomSanitizer. Attempted URL was "${i}".`)}function Gn(i){return Error(`The literal provided to MatIconRegistry was not trusted as safe HTML by Angular's DomSanitizer. Attempted literal was "${i}".`)}var G=class{url;svgText;options;svgElement=null;constructor(n,t,e){this.url=n,this.svgText=t,this.options=e}},Qn=(()=>{class i{_httpClient;_sanitizer;_errorHandler;_document;_svgIconConfigs=new Map;_iconSetConfigs=new Map;_cachedIconsByUrl=new Map;_inProgressUrlFetches=new Map;_fontCssClassesByAlias=new Map;_resolvers=[];_defaultFontSetClass=["material-icons","mat-ligature-font"];constructor(t,e,o,r){this._httpClient=t,this._sanitizer=e,this._errorHandler=r,this._document=o}addSvgIcon(t,e,o){return this.addSvgIconInNamespace("",t,e,o)}addSvgIconLiteral(t,e,o){return this.addSvgIconLiteralInNamespace("",t,e,o)}addSvgIconInNamespace(t,e,o,r){return this._addSvgIconConfig(t,e,new G(o,null,r))}addSvgIconResolver(t){return this._resolvers.push(t),this}addSvgIconLiteralInNamespace(t,e,o,r){let s=this._sanitizer.sanitize(q.HTML,o);if(!s)throw Gn(o);let l=rt(s);return this._addSvgIconConfig(t,e,new G("",l,r))}addSvgIconSet(t,e){return this.addSvgIconSetInNamespace("",t,e)}addSvgIconSetLiteral(t,e){return this.addSvgIconSetLiteralInNamespace("",t,e)}addSvgIconSetInNamespace(t,e,o){return this._addSvgIconSetConfig(t,new G(e,null,o))}addSvgIconSetLiteralInNamespace(t,e,o){let r=this._sanitizer.sanitize(q.HTML,e);if(!r)throw Gn(e);let s=rt(r);return this._addSvgIconSetConfig(t,new G("",s,o))}registerFontClassAlias(t,e=t){return this._fontCssClassesByAlias.set(t,e),this}classNameForFontAlias(t){return this._fontCssClassesByAlias.get(t)||t}setDefaultFontSetClass(...t){return this._defaultFontSetClass=t,this}getDefaultFontSetClass(){return this._defaultFontSetClass}getSvgIconFromUrl(t){let e=this._sanitizer.sanitize(q.RESOURCE_URL,t);if(!e)throw $n(t);let o=this._cachedIconsByUrl.get(e);return o?W(re(o)):this._loadSvgIconFromConfig(new G(t,null)).pipe(ct(r=>this._cachedIconsByUrl.set(e,r)),V(r=>re(r)))}getNamedSvgIcon(t,e=""){let o=qn(e,t),r=this._svgIconConfigs.get(o);if(r)return this._getSvgFromConfig(r);if(r=this._getIconConfigFromResolvers(e,t),r)return this._svgIconConfigs.set(o,r),this._getSvgFromConfig(r);let s=this._iconSetConfigs.get(e);return s?this._getSvgFromIconSetConfigs(t,s):_n(Zn(o))}ngOnDestroy(){this._resolvers=[],this._svgIconConfigs.clear(),this._iconSetConfigs.clear(),this._cachedIconsByUrl.clear()}_getSvgFromConfig(t){return t.svgText?W(re(this._svgElementFromConfig(t))):this._loadSvgIconFromConfig(t).pipe(V(e=>re(e)))}_getSvgFromIconSetConfigs(t,e){let o=this._extractIconWithNameFromAnySet(t,e);if(o)return W(o);let r=e.filter(s=>!s.svgText).map(s=>this._loadSvgIconSetFromConfig(s).pipe(wn(l=>{let u=`Loading icon set URL: ${this._sanitizer.sanitize(q.RESOURCE_URL,s.url)} failed: ${l.message}`;return this._errorHandler.handleError(new Error(u)),W(null)})));return yn(r).pipe(V(()=>{let s=this._extractIconWithNameFromAnySet(t,e);if(!s)throw Zn(t);return s}))}_extractIconWithNameFromAnySet(t,e){for(let o=e.length-1;o>=0;o--){let r=e[o];if(r.svgText&&r.svgText.toString().indexOf(t)>-1){let s=this._svgElementFromConfig(r),l=this._extractSvgIconFromSet(s,t,r.options);if(l)return l}}return null}_loadSvgIconFromConfig(t){return this._fetchIcon(t).pipe(ct(e=>t.svgText=e),V(()=>this._svgElementFromConfig(t)))}_loadSvgIconSetFromConfig(t){return t.svgText?W(null):this._fetchIcon(t).pipe(ct(e=>t.svgText=e))}_extractSvgIconFromSet(t,e,o){let r=t.querySelector(`[id="${e}"]`);if(!r)return null;let s=r.cloneNode(!0);if(s.removeAttribute("id"),s.nodeName.toLowerCase()==="svg")return this._setSvgAttributes(s,o);if(s.nodeName.toLowerCase()==="symbol")return this._setSvgAttributes(this._toSvgElement(s),o);let l=this._svgElementFromString(rt("<svg></svg>"));return l.appendChild(s),this._setSvgAttributes(l,o)}_svgElementFromString(t){let e=this._document.createElement("DIV");e.innerHTML=t;let o=e.querySelector("svg");if(!o)throw Error("<svg> tag not found");return o}_toSvgElement(t){let e=this._svgElementFromString(rt("<svg></svg>")),o=t.attributes;for(let r=0;r<o.length;r++){let{name:s,value:l}=o[r];s!=="id"&&e.setAttribute(s,l)}for(let r=0;r<t.childNodes.length;r++)t.childNodes[r].nodeType===this._document.ELEMENT_NODE&&e.appendChild(t.childNodes[r].cloneNode(!0));return e}_setSvgAttributes(t,e){return t.setAttribute("fit",""),t.setAttribute("height","100%"),t.setAttribute("width","100%"),t.setAttribute("preserveAspectRatio","xMidYMid meet"),t.setAttribute("focusable","false"),e&&e.viewBox&&t.setAttribute("viewBox",e.viewBox),t}_fetchIcon(t){let{url:e,options:o}=t,r=o?.withCredentials??!1;if(!this._httpClient)throw bo();if(e==null)throw Error(`Cannot fetch icon from URL "${e}".`);let s=this._sanitizer.sanitize(q.RESOURCE_URL,e);if(!s)throw $n(e);let l=this._inProgressUrlFetches.get(s);if(l)return l;let c=this._httpClient.get(s,{responseType:"text",withCredentials:r}).pipe(V(u=>rt(u)),En(()=>this._inProgressUrlFetches.delete(s)),xn());return this._inProgressUrlFetches.set(s,c),c}_addSvgIconConfig(t,e,o){return this._svgIconConfigs.set(qn(t,e),o),this}_addSvgIconSetConfig(t,e){let o=this._iconSetConfigs.get(t);return o?o.push(e):this._iconSetConfigs.set(t,[e]),this}_svgElementFromConfig(t){if(!t.svgElement){let e=this._svgElementFromString(t.svgText);this._setSvgAttributes(e,t.options),t.svgElement=e}return t.svgElement}_getIconConfigFromResolvers(t,e){for(let o=0;o<this._resolvers.length;o++){let r=this._resolvers[o](e,t);if(r)return _o(r)?new G(r.url,null,r.options):new G(r,null)}}static \u0275fac=function(e){return new(e||i)(wt(Vn,8),wt(Jt),wt(b,8),wt(Yt))};static \u0275prov=h({token:i,factory:i.\u0275fac,providedIn:"root"})}return i})();function re(i){return i.cloneNode(!0)}function qn(i,n){return i+":"+n}function _o(i){return!!(i.url&&i.options)}var go=["*"],vo=new p("MAT_ICON_DEFAULT_OPTIONS"),yo=new p("mat-icon-location",{providedIn:"root",factory:()=>{let i=a(b),n=i?i.location:null;return{getPathname:()=>n?n.pathname+n.search:""}}}),Jn=["clip-path","color-profile","src","cursor","fill","filter","marker","marker-start","marker-mid","marker-end","mask","stroke"],wo=Jn.map(i=>`[${i}]`).join(", "),Eo=/^url\(['"]?#(.*?)['"]?\)$/,Is=(()=>{class i{_elementRef=a(v);_iconRegistry=a(Qn);_location=a(yo);_errorHandler=a(Yt);_defaultColor;get color(){return this._color||this._defaultColor}set color(t){this._color=t}_color;inline=!1;get svgIcon(){return this._svgIcon}set svgIcon(t){t!==this._svgIcon&&(t?this._updateSvgIcon(t):this._svgIcon&&this._clearSvgElement(),this._svgIcon=t)}_svgIcon;get fontSet(){return this._fontSet}set fontSet(t){let e=this._cleanupFontValue(t);e!==this._fontSet&&(this._fontSet=e,this._updateFontIconClasses())}_fontSet;get fontIcon(){return this._fontIcon}set fontIcon(t){let e=this._cleanupFontValue(t);e!==this._fontIcon&&(this._fontIcon=e,this._updateFontIconClasses())}_fontIcon;_previousFontSetClass=[];_previousFontIconClass;_svgName=null;_svgNamespace=null;_previousPath;_elementsWithExternalReferences;_currentIconFetch=P.EMPTY;constructor(){let t=a(new Fn("aria-hidden"),{optional:!0}),e=a(vo,{optional:!0});e&&(e.color&&(this.color=this._defaultColor=e.color),e.fontSet&&(this.fontSet=e.fontSet)),t||this._elementRef.nativeElement.setAttribute("aria-hidden","true")}_splitIconName(t){if(!t)return["",""];let e=t.split(":");switch(e.length){case 1:return["",e[0]];case 2:return e;default:throw Error(`Invalid icon name: "${t}"`)}}ngOnInit(){this._updateFontIconClasses()}ngAfterViewChecked(){let t=this._elementsWithExternalReferences;if(t&&t.size){let e=this._location.getPathname();e!==this._previousPath&&(this._previousPath=e,this._prependPathToReferences(e))}}ngOnDestroy(){this._currentIconFetch.unsubscribe(),this._elementsWithExternalReferences&&this._elementsWithExternalReferences.clear()}_usingFontIcon(){return!this.svgIcon}_setSvgElement(t){this._clearSvgElement();let e=this._location.getPathname();this._previousPath=e,this._cacheChildrenWithExternalReferences(t),this._prependPathToReferences(e),this._elementRef.nativeElement.appendChild(t)}_clearSvgElement(){let t=this._elementRef.nativeElement,e=t.childNodes.length;for(this._elementsWithExternalReferences&&this._elementsWithExternalReferences.clear();e--;){let o=t.childNodes[e];(o.nodeType!==1||o.nodeName.toLowerCase()==="svg")&&o.remove()}}_updateFontIconClasses(){if(!this._usingFontIcon())return;let t=this._elementRef.nativeElement,e=(this.fontSet?this._iconRegistry.classNameForFontAlias(this.fontSet).split(/ +/):this._iconRegistry.getDefaultFontSetClass()).filter(o=>o.length>0);this._previousFontSetClass.forEach(o=>t.classList.remove(o)),e.forEach(o=>t.classList.add(o)),this._previousFontSetClass=e,this.fontIcon!==this._previousFontIconClass&&!e.includes("mat-ligature-font")&&(this._previousFontIconClass&&t.classList.remove(this._previousFontIconClass),this.fontIcon&&t.classList.add(this.fontIcon),this._previousFontIconClass=this.fontIcon)}_cleanupFontValue(t){return typeof t=="string"?t.trim().split(" ")[0]:t}_prependPathToReferences(t){let e=this._elementsWithExternalReferences;e&&e.forEach((o,r)=>{o.forEach(s=>{r.setAttribute(s.name,`url('${t}#${s.value}')`)})})}_cacheChildrenWithExternalReferences(t){let e=t.querySelectorAll(wo),o=this._elementsWithExternalReferences=this._elementsWithExternalReferences||new Map;for(let r=0;r<e.length;r++)Jn.forEach(s=>{let l=e[r],c=l.getAttribute(s),u=c?c.match(Eo):null;if(u){let d=o.get(l);d||(d=[],o.set(l,d)),d.push({name:s,value:u[1]})}})}_updateSvgIcon(t){if(this._svgNamespace=null,this._svgName=null,this._currentIconFetch.unsubscribe(),t){let[e,o]=this._splitIconName(t);e&&(this._svgNamespace=e),o&&(this._svgName=o),this._currentIconFetch=this._iconRegistry.getNamedSvgIcon(o,e).pipe(Ht(1)).subscribe(r=>this._setSvgElement(r),r=>{let s=`Error retrieving icon ${e}:${o}! ${r.message}`;this._errorHandler.handleError(new Error(s))})}}static \u0275fac=function(e){return new(e||i)};static \u0275cmp=O({type:i,selectors:[["mat-icon"]],hostAttrs:["role","img",1,"mat-icon","notranslate"],hostVars:10,hostBindings:function(e,o){e&2&&(xt("data-mat-icon-type",o._usingFontIcon()?"font":"svg")("data-mat-icon-name",o._svgName||o.fontIcon)("data-mat-icon-namespace",o._svgNamespace||o.fontSet)("fontIcon",o._usingFontIcon()?o.fontIcon:null),Gt(o.color?"mat-"+o.color:""),L("mat-icon-inline",o.inline)("mat-icon-no-color",o.color!=="primary"&&o.color!=="accent"&&o.color!=="warn"))},inputs:{color:"color",inline:[2,"inline","inline",y],svgIcon:"svgIcon",fontSet:"fontSet",fontIcon:"fontIcon"},exportAs:["matIcon"],ngContentSelectors:go,decls:1,vars:0,template:function(e,o){e&1&&($(),N(0))},styles:[`mat-icon, mat-icon.mat-primary, mat-icon.mat-accent, mat-icon.mat-warn {
  color: var(--mat-icon-color, inherit);
}

.mat-icon {
  -webkit-user-select: none;
  user-select: none;
  background-repeat: no-repeat;
  display: inline-block;
  fill: currentColor;
  height: 24px;
  width: 24px;
  overflow: hidden;
}
.mat-icon.mat-icon-inline {
  font-size: inherit;
  height: inherit;
  line-height: inherit;
  width: inherit;
}
.mat-icon.mat-ligature-font[fontIcon]::before {
  content: attr(fontIcon);
}

[dir=rtl] .mat-icon-rtl-mirror {
  transform: scale(-1, 1);
}

.mat-form-field:not(.mat-form-field-appearance-legacy) .mat-form-field-prefix .mat-icon,
.mat-form-field:not(.mat-form-field-appearance-legacy) .mat-form-field-suffix .mat-icon {
  display: block;
}
.mat-form-field:not(.mat-form-field-appearance-legacy) .mat-form-field-prefix .mat-icon-button .mat-icon,
.mat-form-field:not(.mat-form-field-appearance-legacy) .mat-form-field-suffix .mat-icon-button .mat-icon {
  margin: auto;
}
`],encapsulation:2,changeDetection:0})}return i})(),ks=(()=>{class i{static \u0275fac=function(e){return new(e||i)};static \u0275mod=I({type:i});static \u0275inj=C({imports:[U]})}return i})();function Dt(i){return i.buttons===0||i.detail===0}function Ot(i){let n=i.touches&&i.touches[0]||i.changedTouches&&i.changedTouches[0];return!!n&&n.identifier===-1&&(n.radiusX==null||n.radiusX===1)&&(n.radiusY==null||n.radiusY===1)}var Fe;function ti(){if(Fe==null){let i=typeof document<"u"?document.head:null;Fe=!!(i&&(i.createShadowRoot||i.attachShadow))}return Fe}function Ne(i){if(ti()){let n=i.getRootNode?i.getRootNode():null;if(typeof ShadowRoot<"u"&&ShadowRoot&&n instanceof ShadowRoot)return n}return null}function Le(){let i=typeof document<"u"&&document?document.activeElement:null;for(;i&&i.shadowRoot;){let n=i.shadowRoot.activeElement;if(n===i)break;i=n}return i}function T(i){return i.composedPath?i.composedPath()[0]:i.target}var At;function ei(){if(At==null&&typeof window<"u")try{window.addEventListener("test",null,Object.defineProperty({},"passive",{get:()=>At=!0}))}finally{At=At||!1}return At}function bt(i){return ei()?i:!!i.capture}var ni=new p("cdk-input-modality-detector-options"),ii={ignoreKeys:[18,17,224,91,16]},oi=650,Be={passive:!0,capture:!0},ri=(()=>{class i{_platform=a(g);_listenerCleanups;modalityDetected;modalityChanged;get mostRecentModality(){return this._modality.value}_mostRecentTarget=null;_modality=new pn(null);_options;_lastTouchMs=0;_onKeydown=t=>{this._options?.ignoreKeys?.some(e=>e===t.keyCode)||(this._modality.next("keyboard"),this._mostRecentTarget=T(t))};_onMousedown=t=>{Date.now()-this._lastTouchMs<oi||(this._modality.next(Dt(t)?"keyboard":"mouse"),this._mostRecentTarget=T(t))};_onTouchstart=t=>{if(Ot(t)){this._modality.next("keyboard");return}this._lastTouchMs=Date.now(),this._modality.next("touch"),this._mostRecentTarget=T(t)};constructor(){let t=a(_),e=a(b),o=a(ni,{optional:!0});if(this._options=x(x({},ii),o),this.modalityDetected=this._modality.pipe(Ut(1)),this.modalityChanged=this.modalityDetected.pipe(yt()),this._platform.isBrowser){let r=a(j).createRenderer(null,null);this._listenerCleanups=t.runOutsideAngular(()=>[r.listen(e,"keydown",this._onKeydown,Be),r.listen(e,"mousedown",this._onMousedown,Be),r.listen(e,"touchstart",this._onTouchstart,Be)])}}ngOnDestroy(){this._modality.complete(),this._listenerCleanups?.forEach(t=>t())}static \u0275fac=function(e){return new(e||i)};static \u0275prov=h({token:i,factory:i.\u0275fac,providedIn:"root"})}return i})(),Mt=(function(i){return i[i.IMMEDIATE=0]="IMMEDIATE",i[i.EVENTUAL=1]="EVENTUAL",i})(Mt||{}),si=new p("cdk-focus-monitor-default-options"),se=bt({passive:!0,capture:!0}),ae=(()=>{class i{_ngZone=a(_);_platform=a(g);_inputModalityDetector=a(ri);_origin=null;_lastFocusOrigin=null;_windowFocused=!1;_windowFocusTimeoutId;_originTimeoutId;_originFromTouchInteraction=!1;_elementInfo=new Map;_monitoredElementCount=0;_rootNodeFocusListenerCount=new Map;_detectionMode;_windowFocusListener=()=>{this._windowFocused=!0,this._windowFocusTimeoutId=setTimeout(()=>this._windowFocused=!1)};_document=a(b);_stopInputModalityDetector=new f;constructor(){let t=a(si,{optional:!0});this._detectionMode=t?.detectionMode||Mt.IMMEDIATE}_rootNodeFocusAndBlurListener=t=>{let e=T(t);for(let o=e;o;o=o.parentElement)t.type==="focus"?this._onFocus(t,o):this._onBlur(t,o)};monitor(t,e=!1){let o=K(t);if(!this._platform.isBrowser||o.nodeType!==1)return W();let r=Ne(o)||this._document,s=this._elementInfo.get(o);if(s)return e&&(s.checkChildren=!0),s.subject;let l={checkChildren:e,subject:new f,rootNode:r};return this._elementInfo.set(o,l),this._registerGlobalListeners(l),l.subject}stopMonitoring(t){let e=K(t),o=this._elementInfo.get(e);o&&(o.subject.complete(),this._setClasses(e),this._elementInfo.delete(e),this._removeGlobalListeners(o))}focusVia(t,e,o){let r=K(t),s=this._document.activeElement;r===s?this._getClosestElementsInfo(r).forEach(([l,c])=>this._originChanged(l,e,c)):(this._setOrigin(e),typeof r.focus=="function"&&r.focus(o))}ngOnDestroy(){this._elementInfo.forEach((t,e)=>this.stopMonitoring(e))}_getWindow(){return this._document.defaultView||window}_getFocusOrigin(t){return this._origin?this._originFromTouchInteraction?this._shouldBeAttributedToTouch(t)?"touch":"program":this._origin:this._windowFocused&&this._lastFocusOrigin?this._lastFocusOrigin:t&&this._isLastInteractionFromInputLabel(t)?"mouse":"program"}_shouldBeAttributedToTouch(t){return this._detectionMode===Mt.EVENTUAL||!!t?.contains(this._inputModalityDetector._mostRecentTarget)}_setClasses(t,e){t.classList.toggle("cdk-focused",!!e),t.classList.toggle("cdk-touch-focused",e==="touch"),t.classList.toggle("cdk-keyboard-focused",e==="keyboard"),t.classList.toggle("cdk-mouse-focused",e==="mouse"),t.classList.toggle("cdk-program-focused",e==="program")}_setOrigin(t,e=!1){this._ngZone.runOutsideAngular(()=>{if(this._origin=t,this._originFromTouchInteraction=t==="touch"&&e,this._detectionMode===Mt.IMMEDIATE){clearTimeout(this._originTimeoutId);let o=this._originFromTouchInteraction?oi:1;this._originTimeoutId=setTimeout(()=>this._origin=null,o)}})}_onFocus(t,e){let o=this._elementInfo.get(e),r=T(t);!o||!o.checkChildren&&e!==r||this._originChanged(e,this._getFocusOrigin(r),o)}_onBlur(t,e){let o=this._elementInfo.get(e);!o||o.checkChildren&&t.relatedTarget instanceof Node&&e.contains(t.relatedTarget)||(this._setClasses(e),this._emitOrigin(o,null))}_emitOrigin(t,e){t.subject.observers.length&&this._ngZone.run(()=>t.subject.next(e))}_registerGlobalListeners(t){if(!this._platform.isBrowser)return;let e=t.rootNode,o=this._rootNodeFocusListenerCount.get(e)||0;o||this._ngZone.runOutsideAngular(()=>{e.addEventListener("focus",this._rootNodeFocusAndBlurListener,se),e.addEventListener("blur",this._rootNodeFocusAndBlurListener,se)}),this._rootNodeFocusListenerCount.set(e,o+1),++this._monitoredElementCount===1&&(this._ngZone.runOutsideAngular(()=>{this._getWindow().addEventListener("focus",this._windowFocusListener)}),this._inputModalityDetector.modalityDetected.pipe(et(this._stopInputModalityDetector)).subscribe(r=>{this._setOrigin(r,!0)}))}_removeGlobalListeners(t){let e=t.rootNode;if(this._rootNodeFocusListenerCount.has(e)){let o=this._rootNodeFocusListenerCount.get(e);o>1?this._rootNodeFocusListenerCount.set(e,o-1):(e.removeEventListener("focus",this._rootNodeFocusAndBlurListener,se),e.removeEventListener("blur",this._rootNodeFocusAndBlurListener,se),this._rootNodeFocusListenerCount.delete(e))}--this._monitoredElementCount||(this._getWindow().removeEventListener("focus",this._windowFocusListener),this._stopInputModalityDetector.next(),clearTimeout(this._windowFocusTimeoutId),clearTimeout(this._originTimeoutId))}_originChanged(t,e,o){this._setClasses(t,e),this._emitOrigin(o,e),this._lastFocusOrigin=e}_getClosestElementsInfo(t){let e=[];return this._elementInfo.forEach((o,r)=>{(r===t||o.checkChildren&&r.contains(t))&&e.push([r,o])}),e}_isLastInteractionFromInputLabel(t){let{_mostRecentTarget:e,mostRecentModality:o}=this._inputModalityDetector;if(o!=="mouse"||!e||e===t||t.nodeName!=="INPUT"&&t.nodeName!=="TEXTAREA"||t.disabled)return!1;let r=t.labels;if(r){for(let s=0;s<r.length;s++)if(r[s].contains(e))return!0}return!1}static \u0275fac=function(e){return new(e||i)};static \u0275prov=h({token:i,factory:i.\u0275fac,providedIn:"root"})}return i})(),xo=(()=>{class i{_elementRef=a(v);_focusMonitor=a(ae);_monitorSubscription;_focusOrigin=null;cdkFocusChange=new F;constructor(){}get focusOrigin(){return this._focusOrigin}ngAfterViewInit(){let t=this._elementRef.nativeElement;this._monitorSubscription=this._focusMonitor.monitor(t,t.nodeType===1&&t.hasAttribute("cdkMonitorSubtreeFocus")).subscribe(e=>{this._focusOrigin=e,this.cdkFocusChange.emit(e)})}ngOnDestroy(){this._focusMonitor.stopMonitoring(this._elementRef),this._monitorSubscription?.unsubscribe()}static \u0275fac=function(e){return new(e||i)};static \u0275dir=k({type:i,selectors:[["","cdkMonitorElementFocus",""],["","cdkMonitorSubtreeFocus",""]],outputs:{cdkFocusChange:"cdkFocusChange"},exportAs:["cdkMonitorFocus"]})}return i})();function st(i){return Array.isArray(i)?i:[i]}var ai=new Set,at,le=(()=>{class i{_platform=a(g);_nonce=a(Rn,{optional:!0});_matchMedia;constructor(){this._matchMedia=this._platform.isBrowser&&window.matchMedia?window.matchMedia.bind(window):So}matchMedia(t){return(this._platform.WEBKIT||this._platform.BLINK)&&Co(t,this._nonce),this._matchMedia(t)}static \u0275fac=function(e){return new(e||i)};static \u0275prov=h({token:i,factory:i.\u0275fac,providedIn:"root"})}return i})();function Co(i,n){if(!ai.has(i))try{at||(at=document.createElement("style"),n&&at.setAttribute("nonce",n),at.setAttribute("type","text/css"),document.head.appendChild(at)),at.sheet&&(at.sheet.insertRule(`@media ${i} {body{ }}`,0),ai.add(i))}catch(t){console.error(t)}}function So(i){return{matches:i==="all"||i==="",media:i,addListener:()=>{},removeListener:()=>{}}}var ze=(()=>{class i{_mediaMatcher=a(le);_zone=a(_);_queries=new Map;_destroySubject=new f;constructor(){}ngOnDestroy(){this._destroySubject.next(),this._destroySubject.complete()}isMatched(t){return li(st(t)).some(o=>this._registerQuery(o).mql.matches)}observe(t){let o=li(st(t)).map(s=>this._registerQuery(s).observable),r=gn(o);return r=vn(r.pipe(Ht(1)),r.pipe(Ut(1),jt(0))),r.pipe(V(s=>{let l={matches:!1,breakpoints:{}};return s.forEach(({matches:c,query:u})=>{l.matches=l.matches||c,l.breakpoints[u]=c}),l}))}_registerQuery(t){if(this._queries.has(t))return this._queries.get(t);let e=this._mediaMatcher.matchMedia(t),r={observable:new vt(s=>{let l=c=>this._zone.run(()=>s.next(c));return e.addListener(l),()=>{e.removeListener(l)}}).pipe(Wt(e),V(({matches:s})=>({query:t,matches:s})),et(this._destroySubject)),mql:e};return this._queries.set(t,r),r}static \u0275fac=function(e){return new(e||i)};static \u0275prov=h({token:i,factory:i.\u0275fac,providedIn:"root"})}return i})();function li(i){return i.map(n=>n.split(",")).reduce((n,t)=>n.concat(t)).map(n=>n.trim())}var Io=(()=>{class i{create(t){return typeof MutationObserver>"u"?null:new MutationObserver(t)}static \u0275fac=function(e){return new(e||i)};static \u0275prov=h({token:i,factory:i.\u0275fac,providedIn:"root"})}return i})();var ci=(()=>{class i{static \u0275fac=function(e){return new(e||i)};static \u0275mod=I({type:i});static \u0275inj=C({providers:[Io]})}return i})();var ko=(()=>{class i{_platform=a(g);constructor(){}isDisabled(t){return t.hasAttribute("disabled")}isVisible(t){return Do(t)&&getComputedStyle(t).visibility==="visible"}isTabbable(t){if(!this._platform.isBrowser)return!1;let e=Ro(Lo(t));if(e&&(di(e)===-1||!this.isVisible(e)))return!1;let o=t.nodeName.toLowerCase(),r=di(t);return t.hasAttribute("contenteditable")?r!==-1:o==="iframe"||o==="object"||this._platform.WEBKIT&&this._platform.IOS&&!Fo(t)?!1:o==="audio"?t.hasAttribute("controls")?r!==-1:!1:o==="video"?r===-1?!1:r!==null?!0:this._platform.FIREFOX||t.hasAttribute("controls"):t.tabIndex>=0}isFocusable(t,e){return No(t)&&!this.isDisabled(t)&&(e?.ignoreVisibility||this.isVisible(t))}static \u0275fac=function(e){return new(e||i)};static \u0275prov=h({token:i,factory:i.\u0275fac,providedIn:"root"})}return i})();function Ro(i){try{return i.frameElement}catch(n){return null}}function Do(i){return!!(i.offsetWidth||i.offsetHeight||typeof i.getClientRects=="function"&&i.getClientRects().length)}function Oo(i){let n=i.nodeName.toLowerCase();return n==="input"||n==="select"||n==="button"||n==="textarea"}function Ao(i){return To(i)&&i.type=="hidden"}function Mo(i){return Po(i)&&i.hasAttribute("href")}function To(i){return i.nodeName.toLowerCase()=="input"}function Po(i){return i.nodeName.toLowerCase()=="a"}function mi(i){if(!i.hasAttribute("tabindex")||i.tabIndex===void 0)return!1;let n=i.getAttribute("tabindex");return!!(n&&!isNaN(parseInt(n,10)))}function di(i){if(!mi(i))return null;let n=parseInt(i.getAttribute("tabindex")||"",10);return isNaN(n)?-1:n}function Fo(i){let n=i.nodeName.toLowerCase(),t=n==="input"&&i.type;return t==="text"||t==="password"||n==="select"||n==="textarea"}function No(i){return Ao(i)?!1:Oo(i)||Mo(i)||i.hasAttribute("contenteditable")||mi(i)}function Lo(i){return i.ownerDocument&&i.ownerDocument.defaultView||window}var je=class{_element;_checker;_ngZone;_document;_injector;_startAnchor=null;_endAnchor=null;_hasAttached=!1;startAnchorListener=()=>this.focusLastTabbableElement();endAnchorListener=()=>this.focusFirstTabbableElement();get enabled(){return this._enabled}set enabled(n){this._enabled=n,this._startAnchor&&this._endAnchor&&(this._toggleAnchorTabIndex(n,this._startAnchor),this._toggleAnchorTabIndex(n,this._endAnchor))}_enabled=!0;constructor(n,t,e,o,r=!1,s){this._element=n,this._checker=t,this._ngZone=e,this._document=o,this._injector=s,r||this.attachAnchors()}destroy(){let n=this._startAnchor,t=this._endAnchor;n&&(n.removeEventListener("focus",this.startAnchorListener),n.remove()),t&&(t.removeEventListener("focus",this.endAnchorListener),t.remove()),this._startAnchor=this._endAnchor=null,this._hasAttached=!1}attachAnchors(){return this._hasAttached?!0:(this._ngZone.runOutsideAngular(()=>{this._startAnchor||(this._startAnchor=this._createAnchor(),this._startAnchor.addEventListener("focus",this.startAnchorListener)),this._endAnchor||(this._endAnchor=this._createAnchor(),this._endAnchor.addEventListener("focus",this.endAnchorListener))}),this._element.parentNode&&(this._element.parentNode.insertBefore(this._startAnchor,this._element),this._element.parentNode.insertBefore(this._endAnchor,this._element.nextSibling),this._hasAttached=!0),this._hasAttached)}focusInitialElementWhenReady(n){return new Promise(t=>{this._executeOnStable(()=>t(this.focusInitialElement(n)))})}focusFirstTabbableElementWhenReady(n){return new Promise(t=>{this._executeOnStable(()=>t(this.focusFirstTabbableElement(n)))})}focusLastTabbableElementWhenReady(n){return new Promise(t=>{this._executeOnStable(()=>t(this.focusLastTabbableElement(n)))})}_getRegionBoundary(n){let t=this._element.querySelectorAll(`[cdk-focus-region-${n}], [cdkFocusRegion${n}], [cdk-focus-${n}]`);return n=="start"?t.length?t[0]:this._getFirstTabbableElement(this._element):t.length?t[t.length-1]:this._getLastTabbableElement(this._element)}focusInitialElement(n){let t=this._element.querySelector("[cdk-focus-initial], [cdkFocusInitial]");if(t){if(!this._checker.isFocusable(t)){let e=this._getFirstTabbableElement(t);return e?.focus(n),!!e}return t.focus(n),!0}return this.focusFirstTabbableElement(n)}focusFirstTabbableElement(n){let t=this._getRegionBoundary("start");return t&&t.focus(n),!!t}focusLastTabbableElement(n){let t=this._getRegionBoundary("end");return t&&t.focus(n),!!t}hasAttached(){return this._hasAttached}_getFirstTabbableElement(n){if(this._checker.isFocusable(n)&&this._checker.isTabbable(n))return n;let t=n.children;for(let e=0;e<t.length;e++){let o=t[e].nodeType===this._document.ELEMENT_NODE?this._getFirstTabbableElement(t[e]):null;if(o)return o}return null}_getLastTabbableElement(n){if(this._checker.isFocusable(n)&&this._checker.isTabbable(n))return n;let t=n.children;for(let e=t.length-1;e>=0;e--){let o=t[e].nodeType===this._document.ELEMENT_NODE?this._getLastTabbableElement(t[e]):null;if(o)return o}return null}_createAnchor(){let n=this._document.createElement("div");return this._toggleAnchorTabIndex(this._enabled,n),n.classList.add("cdk-visually-hidden"),n.classList.add("cdk-focus-trap-anchor"),n.setAttribute("aria-hidden","true"),n}_toggleAnchorTabIndex(n,t){n?t.setAttribute("tabindex","0"):t.removeAttribute("tabindex")}toggleAnchors(n){this._startAnchor&&this._endAnchor&&(this._toggleAnchorTabIndex(n,this._startAnchor),this._toggleAnchorTabIndex(n,this._endAnchor))}_executeOnStable(n){this._injector?nt(n,{injector:this._injector}):setTimeout(n)}},pi=(()=>{class i{_checker=a(ko);_ngZone=a(_);_document=a(b);_injector=a(S);constructor(){a(B).load(oe)}create(t,e=!1){return new je(t,this._checker,this._ngZone,this._document,e,this._injector)}static \u0275fac=function(e){return new(e||i)};static \u0275prov=h({token:i,factory:i.\u0275fac,providedIn:"root"})}return i})(),Bo=(()=>{class i{_elementRef=a(v);_focusTrapFactory=a(pi);focusTrap=void 0;_previouslyFocusedElement=null;get enabled(){return this.focusTrap?.enabled||!1}set enabled(t){this.focusTrap&&(this.focusTrap.enabled=t)}autoCapture=!1;constructor(){a(g).isBrowser&&(this.focusTrap=this._focusTrapFactory.create(this._elementRef.nativeElement,!0))}ngOnDestroy(){this.focusTrap?.destroy(),this._previouslyFocusedElement&&(this._previouslyFocusedElement.focus(),this._previouslyFocusedElement=null)}ngAfterContentInit(){this.focusTrap?.attachAnchors(),this.autoCapture&&this._captureFocus()}ngDoCheck(){this.focusTrap&&!this.focusTrap.hasAttached()&&this.focusTrap.attachAnchors()}ngOnChanges(t){let e=t.autoCapture;e&&!e.firstChange&&this.autoCapture&&this.focusTrap?.hasAttached()&&this._captureFocus()}_captureFocus(){this._previouslyFocusedElement=Le(),this.focusTrap?.focusInitialElementWhenReady()}static \u0275fac=function(e){return new(e||i)};static \u0275dir=k({type:i,selectors:[["","cdkTrapFocus",""]],inputs:{enabled:[2,"cdkTrapFocus","enabled",y],autoCapture:[2,"cdkTrapFocusAutoCapture","autoCapture",y]},exportAs:["cdkTrapFocus"],features:[ut]})}return i})(),fi=new p("liveAnnouncerElement",{providedIn:"root",factory:()=>null}),bi=new p("LIVE_ANNOUNCER_DEFAULT_OPTIONS"),zo=0,Vo=(()=>{class i{_ngZone=a(_);_defaultOptions=a(bi,{optional:!0});_liveElement;_document=a(b);_sanitizer=a(Jt);_previousTimeout;_currentPromise;_currentResolve;constructor(){let t=a(fi,{optional:!0});this._liveElement=t||this._createLiveElement()}announce(t,...e){let o=this._defaultOptions,r,s;return e.length===1&&typeof e[0]=="number"?s=e[0]:[r,s]=e,this.clear(),clearTimeout(this._previousTimeout),r||(r=o&&o.politeness?o.politeness:"polite"),s==null&&o&&(s=o.duration),this._liveElement.setAttribute("aria-live",r),this._liveElement.id&&this._exposeAnnouncerToModals(this._liveElement.id),this._ngZone.runOutsideAngular(()=>(this._currentPromise||(this._currentPromise=new Promise(l=>this._currentResolve=l)),clearTimeout(this._previousTimeout),this._previousTimeout=setTimeout(()=>{!t||typeof t=="string"?this._liveElement.textContent=t:Kn(this._liveElement,t,this._sanitizer),typeof s=="number"&&(this._previousTimeout=setTimeout(()=>this.clear(),s)),this._currentResolve?.(),this._currentPromise=this._currentResolve=void 0},100),this._currentPromise))}clear(){this._liveElement&&(this._liveElement.textContent="")}ngOnDestroy(){clearTimeout(this._previousTimeout),this._liveElement?.remove(),this._liveElement=null,this._currentResolve?.(),this._currentPromise=this._currentResolve=void 0}_createLiveElement(){let t="cdk-live-announcer-element",e=this._document.getElementsByClassName(t),o=this._document.createElement("div");for(let r=0;r<e.length;r++)e[r].remove();return o.classList.add(t),o.classList.add("cdk-visually-hidden"),o.setAttribute("aria-atomic","true"),o.setAttribute("aria-live","polite"),o.id=`cdk-live-announcer-${zo++}`,this._document.body.appendChild(o),o}_exposeAnnouncerToModals(t){let e=this._document.querySelectorAll('body > .cdk-overlay-container [aria-modal="true"]');for(let o=0;o<e.length;o++){let r=e[o],s=r.getAttribute("aria-owns");s?s.indexOf(t)===-1&&r.setAttribute("aria-owns",s+" "+t):r.setAttribute("aria-owns",t)}}static \u0275fac=function(e){return new(e||i)};static \u0275prov=h({token:i,factory:i.\u0275fac,providedIn:"root"})}return i})();var Q=(function(i){return i[i.NONE=0]="NONE",i[i.BLACK_ON_WHITE=1]="BLACK_ON_WHITE",i[i.WHITE_ON_BLACK=2]="WHITE_ON_BLACK",i})(Q||{}),ui="cdk-high-contrast-black-on-white",hi="cdk-high-contrast-white-on-black",Ve="cdk-high-contrast-active",_i=(()=>{class i{_platform=a(g);_hasCheckedHighContrastMode=!1;_document=a(b);_breakpointSubscription;constructor(){this._breakpointSubscription=a(ze).observe("(forced-colors: active)").subscribe(()=>{this._hasCheckedHighContrastMode&&(this._hasCheckedHighContrastMode=!1,this._applyBodyHighContrastModeCssClasses())})}getHighContrastMode(){if(!this._platform.isBrowser)return Q.NONE;let t=this._document.createElement("div");t.style.backgroundColor="rgb(1,2,3)",t.style.position="absolute",this._document.body.appendChild(t);let e=this._document.defaultView||window,o=e&&e.getComputedStyle?e.getComputedStyle(t):null,r=(o&&o.backgroundColor||"").replace(/ /g,"");switch(t.remove(),r){case"rgb(0,0,0)":case"rgb(45,50,54)":case"rgb(32,32,32)":return Q.WHITE_ON_BLACK;case"rgb(255,255,255)":case"rgb(255,250,239)":return Q.BLACK_ON_WHITE}return Q.NONE}ngOnDestroy(){this._breakpointSubscription.unsubscribe()}_applyBodyHighContrastModeCssClasses(){if(!this._hasCheckedHighContrastMode&&this._platform.isBrowser&&this._document.body){let t=this._document.body.classList;t.remove(Ve,ui,hi),this._hasCheckedHighContrastMode=!0;let e=this.getHighContrastMode();e===Q.BLACK_ON_WHITE?t.add(Ve,ui):e===Q.WHITE_ON_BLACK&&t.add(Ve,hi)}}static \u0275fac=function(e){return new(e||i)};static \u0275prov=h({token:i,factory:i.\u0275fac,providedIn:"root"})}return i})(),jo=(()=>{class i{constructor(){a(_i)._applyBodyHighContrastModeCssClasses()}static \u0275fac=function(e){return new(e||i)};static \u0275mod=I({type:i});static \u0275inj=C({imports:[ci]})}return i})();var Ho=200,ce=class{_letterKeyStream=new f;_items=[];_selectedItemIndex=-1;_pressedLetters=[];_skipPredicateFn;_selectedItem=new f;selectedItem=this._selectedItem;constructor(n,t){let e=typeof t?.debounceInterval=="number"?t.debounceInterval:Ho;t?.skipPredicate&&(this._skipPredicateFn=t.skipPredicate),this.setItems(n),this._setupKeyHandler(e)}destroy(){this._pressedLetters=[],this._letterKeyStream.complete(),this._selectedItem.complete()}setCurrentSelectedItemIndex(n){this._selectedItemIndex=n}setItems(n){this._items=n}handleKey(n){let t=n.keyCode;n.key&&n.key.length===1?this._letterKeyStream.next(n.key.toLocaleUpperCase()):(t>=65&&t<=90||t>=48&&t<=57)&&this._letterKeyStream.next(String.fromCharCode(t))}isTyping(){return this._pressedLetters.length>0}reset(){this._pressedLetters=[]}_setupKeyHandler(n){this._letterKeyStream.pipe(ct(t=>this._pressedLetters.push(t)),jt(n),tt(()=>this._pressedLetters.length>0),V(()=>this._pressedLetters.join("").toLocaleUpperCase())).subscribe(t=>{for(let e=1;e<this._items.length+1;e++){let o=(this._selectedItemIndex+e)%this._items.length,r=this._items[o];if(!this._skipPredicateFn?.(r)&&r.getLabel?.().toLocaleUpperCase().trim().indexOf(t)===0){this._selectedItem.next(r);break}}this._pressedLetters=[]})}};function de(i,...n){return n.length?n.some(t=>i[t]):i.altKey||i.shiftKey||i.ctrlKey||i.metaKey}var ue=class{_items;_activeItemIndex=Z(-1);_activeItem=Z(null);_wrap=!1;_typeaheadSubscription=P.EMPTY;_itemChangesSubscription;_vertical=!0;_horizontal=null;_allowedModifierKeys=[];_homeAndEnd=!1;_pageUpAndDown={enabled:!1,delta:10};_effectRef;_typeahead;_skipPredicateFn=n=>n.disabled;constructor(n,t){this._items=n,n instanceof ke?this._itemChangesSubscription=n.changes.subscribe(e=>this._itemsChanged(e.toArray())):De(n)&&(this._effectRef=Xt(()=>this._itemsChanged(n()),{injector:t}))}tabOut=new f;change=new f;skipPredicate(n){return this._skipPredicateFn=n,this}withWrap(n=!0){return this._wrap=n,this}withVerticalOrientation(n=!0){return this._vertical=n,this}withHorizontalOrientation(n){return this._horizontal=n,this}withAllowedModifierKeys(n){return this._allowedModifierKeys=n,this}withTypeAhead(n=200){this._typeaheadSubscription.unsubscribe();let t=this._getItemsArray();return this._typeahead=new ce(t,{debounceInterval:typeof n=="number"?n:void 0,skipPredicate:e=>this._skipPredicateFn(e)}),this._typeaheadSubscription=this._typeahead.selectedItem.subscribe(e=>{this.setActiveItem(e)}),this}cancelTypeahead(){return this._typeahead?.reset(),this}withHomeAndEnd(n=!0){return this._homeAndEnd=n,this}withPageUpDown(n=!0,t=10){return this._pageUpAndDown={enabled:n,delta:t},this}setActiveItem(n){let t=this._activeItem();this.updateActiveItem(n),this._activeItem()!==t&&this.change.next(this._activeItemIndex())}onKeydown(n){let t=n.keyCode,o=["altKey","ctrlKey","metaKey","shiftKey"].every(r=>!n[r]||this._allowedModifierKeys.indexOf(r)>-1);switch(t){case 9:this.tabOut.next();return;case 40:if(this._vertical&&o){this.setNextItemActive();break}else return;case 38:if(this._vertical&&o){this.setPreviousItemActive();break}else return;case 39:if(this._horizontal&&o){this._horizontal==="rtl"?this.setPreviousItemActive():this.setNextItemActive();break}else return;case 37:if(this._horizontal&&o){this._horizontal==="rtl"?this.setNextItemActive():this.setPreviousItemActive();break}else return;case 36:if(this._homeAndEnd&&o){this.setFirstItemActive();break}else return;case 35:if(this._homeAndEnd&&o){this.setLastItemActive();break}else return;case 33:if(this._pageUpAndDown.enabled&&o){let r=this._activeItemIndex()-this._pageUpAndDown.delta;this._setActiveItemByIndex(r>0?r:0,1);break}else return;case 34:if(this._pageUpAndDown.enabled&&o){let r=this._activeItemIndex()+this._pageUpAndDown.delta,s=this._getItemsArray().length;this._setActiveItemByIndex(r<s?r:s-1,-1);break}else return;default:(o||de(n,"shiftKey"))&&this._typeahead?.handleKey(n);return}this._typeahead?.reset(),n.preventDefault()}get activeItemIndex(){return this._activeItemIndex()}get activeItem(){return this._activeItem()}isTyping(){return!!this._typeahead&&this._typeahead.isTyping()}setFirstItemActive(){this._setActiveItemByIndex(0,1)}setLastItemActive(){this._setActiveItemByIndex(this._getItemsArray().length-1,-1)}setNextItemActive(){this._activeItemIndex()<0?this.setFirstItemActive():this._setActiveItemByDelta(1)}setPreviousItemActive(){this._activeItemIndex()<0&&this._wrap?this.setLastItemActive():this._setActiveItemByDelta(-1)}updateActiveItem(n){let t=this._getItemsArray(),e=typeof n=="number"?n:t.indexOf(n),o=t[e];this._activeItem.set(o??null),this._activeItemIndex.set(e),this._typeahead?.setCurrentSelectedItemIndex(e)}destroy(){this._typeaheadSubscription.unsubscribe(),this._itemChangesSubscription?.unsubscribe(),this._effectRef?.destroy(),this._typeahead?.destroy(),this.tabOut.complete(),this.change.complete()}_setActiveItemByDelta(n){this._wrap?this._setActiveInWrapMode(n):this._setActiveInDefaultMode(n)}_setActiveInWrapMode(n){let t=this._getItemsArray();for(let e=1;e<=t.length;e++){let o=(this._activeItemIndex()+n*e+t.length)%t.length,r=t[o];if(!this._skipPredicateFn(r)){this.setActiveItem(o);return}}}_setActiveInDefaultMode(n){this._setActiveItemByIndex(this._activeItemIndex()+n,n)}_setActiveItemByIndex(n,t){let e=this._getItemsArray();if(e[n]){for(;this._skipPredicateFn(e[n]);)if(n+=t,!e[n])return;this.setActiveItem(n)}}_getItemsArray(){return De(this._items)?this._items():this._items instanceof ke?this._items.toArray():this._items}_itemsChanged(n){this._typeahead?.setItems(n);let t=this._activeItem();if(t){let e=n.indexOf(t);e>-1&&e!==this._activeItemIndex()&&(this._activeItemIndex.set(e),this._typeahead?.setCurrentSelectedItemIndex(e))}}};var He=class extends ue{setActiveItem(n){this.activeItem&&this.activeItem.setInactiveStyles(),super.setActiveItem(n),this.activeItem&&this.activeItem.setActiveStyles()}};var Ue={},Tt=class i{_appId=a(Kt);static _infix=`a${Math.floor(Math.random()*1e5).toString()}`;getId(n,t=!1){return this._appId!=="ng"&&(n+=this._appId),Ue.hasOwnProperty(n)||(Ue[n]=0),`${n}${t?i._infix+"-":""}${Ue[n]++}`}static \u0275fac=function(t){return new(t||i)};static \u0275prov=h({token:i,factory:i.\u0275fac,providedIn:"root"})};var vi=" ";function Uo(i,n,t){let e=me(i,n);t=t.trim(),!e.some(o=>o.trim()===t)&&(e.push(t),i.setAttribute(n,e.join(vi)))}function Wo(i,n,t){let e=me(i,n);t=t.trim();let o=e.filter(r=>r!==t);o.length?i.setAttribute(n,o.join(vi)):i.removeAttribute(n)}function me(i,n){return i.getAttribute(n)?.match(/\S+/g)??[]}var yi="cdk-describedby-message",he="cdk-describedby-host",Ye=0,Ja=(()=>{class i{_platform=a(g);_document=a(b);_messageRegistry=new Map;_messagesContainer=null;_id=`${Ye++}`;constructor(){a(B).load(oe),this._id=a(Kt)+"-"+Ye++}describe(t,e,o){if(!this._canBeDescribed(t,e))return;let r=We(e,o);typeof e!="string"?(gi(e,this._id),this._messageRegistry.set(r,{messageElement:e,referenceCount:0})):this._messageRegistry.has(r)||this._createMessageElement(e,o),this._isElementDescribedByMessage(t,r)||this._addMessageReference(t,r)}removeDescription(t,e,o){if(!e||!this._isElementNode(t))return;let r=We(e,o);if(this._isElementDescribedByMessage(t,r)&&this._removeMessageReference(t,r),typeof e=="string"){let s=this._messageRegistry.get(r);s&&s.referenceCount===0&&this._deleteMessageElement(r)}this._messagesContainer?.childNodes.length===0&&(this._messagesContainer.remove(),this._messagesContainer=null)}ngOnDestroy(){let t=this._document.querySelectorAll(`[${he}="${this._id}"]`);for(let e=0;e<t.length;e++)this._removeCdkDescribedByReferenceIds(t[e]),t[e].removeAttribute(he);this._messagesContainer?.remove(),this._messagesContainer=null,this._messageRegistry.clear()}_createMessageElement(t,e){let o=this._document.createElement("div");gi(o,this._id),o.textContent=t,e&&o.setAttribute("role",e),this._createMessagesContainer(),this._messagesContainer.appendChild(o),this._messageRegistry.set(We(t,e),{messageElement:o,referenceCount:0})}_deleteMessageElement(t){this._messageRegistry.get(t)?.messageElement?.remove(),this._messageRegistry.delete(t)}_createMessagesContainer(){if(this._messagesContainer)return;let t="cdk-describedby-message-container",e=this._document.querySelectorAll(`.${t}[platform="server"]`);for(let r=0;r<e.length;r++)e[r].remove();let o=this._document.createElement("div");o.style.visibility="hidden",o.classList.add(t),o.classList.add("cdk-visually-hidden"),this._platform.isBrowser||o.setAttribute("platform","server"),this._document.body.appendChild(o),this._messagesContainer=o}_removeCdkDescribedByReferenceIds(t){let e=me(t,"aria-describedby").filter(o=>o.indexOf(yi)!=0);t.setAttribute("aria-describedby",e.join(" "))}_addMessageReference(t,e){let o=this._messageRegistry.get(e);Uo(t,"aria-describedby",o.messageElement.id),t.setAttribute(he,this._id),o.referenceCount++}_removeMessageReference(t,e){let o=this._messageRegistry.get(e);o.referenceCount--,Wo(t,"aria-describedby",o.messageElement.id),t.removeAttribute(he)}_isElementDescribedByMessage(t,e){let o=me(t,"aria-describedby"),r=this._messageRegistry.get(e),s=r&&r.messageElement.id;return!!s&&o.indexOf(s)!=-1}_canBeDescribed(t,e){if(!this._isElementNode(t))return!1;if(e&&typeof e=="object")return!0;let o=e==null?"":`${e}`.trim(),r=t.getAttribute("aria-label");return o?!r||r.trim()!==o:!1}_isElementNode(t){return t.nodeType===this._document.ELEMENT_NODE}static \u0275fac=function(e){return new(e||i)};static \u0275prov=h({token:i,factory:i.\u0275fac,providedIn:"root"})}return i})();function We(i,n){return typeof i=="string"?`${n||""}/${i}`:i}function gi(i,n){i.id||(i.id=`${yi}-${n}-${Ye++}`)}function Xe(){return typeof __karma__<"u"&&!!__karma__||typeof jasmine<"u"&&!!jasmine||typeof jest<"u"&&!!jest||typeof Mocha<"u"&&!!Mocha}var _t,wi=["color","button","checkbox","date","datetime-local","email","file","hidden","image","month","number","password","radio","range","reset","search","submit","tel","text","time","url","week"];function al(){if(_t)return _t;if(typeof document!="object"||!document)return _t=new Set(wi),_t;let i=document.createElement("input");return _t=new Set(wi.filter(n=>(i.setAttribute("type",n),i.type===n))),_t}var hl={XSmall:"(max-width: 599.98px)",Small:"(min-width: 600px) and (max-width: 959.98px)",Medium:"(min-width: 960px) and (max-width: 1279.98px)",Large:"(min-width: 1280px) and (max-width: 1919.98px)",XLarge:"(min-width: 1920px)",Handset:"(max-width: 599.98px) and (orientation: portrait), (max-width: 959.98px) and (orientation: landscape)",Tablet:"(min-width: 600px) and (max-width: 839.98px) and (orientation: portrait), (min-width: 960px) and (max-width: 1279.98px) and (orientation: landscape)",Web:"(min-width: 840px) and (orientation: portrait), (min-width: 1280px) and (orientation: landscape)",HandsetPortrait:"(max-width: 599.98px) and (orientation: portrait)",TabletPortrait:"(min-width: 600px) and (max-width: 839.98px) and (orientation: portrait)",WebPortrait:"(min-width: 840px) and (orientation: portrait)",HandsetLandscape:"(max-width: 959.98px) and (orientation: landscape)",TabletLandscape:"(min-width: 960px) and (max-width: 1279.98px) and (orientation: landscape)",WebLandscape:"(min-width: 1280px) and (orientation: landscape)"};var Yo=new p("MATERIAL_ANIMATIONS"),Ei=null;function Xo(){return a(Yo,{optional:!0})?.animationsDisabled||a(Zt,{optional:!0})==="NoopAnimations"?"di-disabled":(Ei??=a(le).matchMedia("(prefers-reduced-motion)").matches,Ei?"reduced-motion":"enabled")}function gt(){return Xo()!=="enabled"}function E(i){return i==null?"":typeof i=="string"?i:`${i}px`}function vl(i){return i!=null&&`${i}`!="false"}function yl(i,n=/\s+/){let t=[];if(i!=null){let e=Array.isArray(i)?i:`${i}`.split(n);for(let o of e){let r=`${o}`.trim();r&&t.push(r)}}return t}var z=(function(i){return i[i.FADING_IN=0]="FADING_IN",i[i.VISIBLE=1]="VISIBLE",i[i.FADING_OUT=2]="FADING_OUT",i[i.HIDDEN=3]="HIDDEN",i})(z||{}),Ke=class{_renderer;element;config;_animationForciblyDisabledThroughCss;state=z.HIDDEN;constructor(n,t,e,o=!1){this._renderer=n,this.element=t,this.config=e,this._animationForciblyDisabledThroughCss=o}fadeOut(){this._renderer.fadeOutRipple(this)}},xi=bt({passive:!0,capture:!0}),Ze=class{_events=new Map;addHandler(n,t,e,o){let r=this._events.get(t);if(r){let s=r.get(e);s?s.add(o):r.set(e,new Set([o]))}else this._events.set(t,new Map([[e,new Set([o])]])),n.runOutsideAngular(()=>{document.addEventListener(t,this._delegateEventHandler,xi)})}removeHandler(n,t,e){let o=this._events.get(n);if(!o)return;let r=o.get(t);r&&(r.delete(e),r.size===0&&o.delete(t),o.size===0&&(this._events.delete(n),document.removeEventListener(n,this._delegateEventHandler,xi)))}_delegateEventHandler=n=>{let t=T(n);t&&this._events.get(n.type)?.forEach((e,o)=>{(o===t||o.contains(t))&&e.forEach(r=>r.handleEvent(n))})}},Pt={enterDuration:225,exitDuration:150},Ko=800,Ci=bt({passive:!0,capture:!0}),Si=["mousedown","touchstart"],Ii=["mouseup","mouseleave","touchend","touchcancel"],Zo=(()=>{class i{static \u0275fac=function(e){return new(e||i)};static \u0275cmp=O({type:i,selectors:[["ng-component"]],hostAttrs:["mat-ripple-style-loader",""],decls:0,vars:0,template:function(e,o){},styles:[`.mat-ripple {
  overflow: hidden;
  position: relative;
}
.mat-ripple:not(:empty) {
  transform: translateZ(0);
}

.mat-ripple.mat-ripple-unbounded {
  overflow: visible;
}

.mat-ripple-element {
  position: absolute;
  border-radius: 50%;
  pointer-events: none;
  transition: opacity, transform 0ms cubic-bezier(0, 0, 0.2, 1);
  transform: scale3d(0, 0, 0);
  background-color: var(--mat-ripple-color, color-mix(in srgb, var(--mat-sys-on-surface) 10%, transparent));
}
@media (forced-colors: active) {
  .mat-ripple-element {
    display: none;
  }
}
.cdk-drag-preview .mat-ripple-element, .cdk-drag-placeholder .mat-ripple-element {
  display: none;
}
`],encapsulation:2,changeDetection:0})}return i})(),Ft=class i{_target;_ngZone;_platform;_containerElement;_triggerElement=null;_isPointerDown=!1;_activeRipples=new Map;_mostRecentTransientRipple=null;_lastTouchStartEvent;_pointerUpEventsRegistered=!1;_containerRect=null;static _eventManager=new Ze;constructor(n,t,e,o,r){this._target=n,this._ngZone=t,this._platform=o,o.isBrowser&&(this._containerElement=K(e)),r&&r.get(B).load(Zo)}fadeInRipple(n,t,e={}){let o=this._containerRect=this._containerRect||this._containerElement.getBoundingClientRect(),r=x(x({},Pt),e.animation);e.centered&&(n=o.left+o.width/2,t=o.top+o.height/2);let s=e.radius||$o(n,t,o),l=n-o.left,c=t-o.top,u=r.enterDuration,d=document.createElement("div");d.classList.add("mat-ripple-element"),d.style.left=`${l-s}px`,d.style.top=`${c-s}px`,d.style.height=`${s*2}px`,d.style.width=`${s*2}px`,e.color!=null&&(d.style.backgroundColor=e.color),d.style.transitionDuration=`${u}ms`,this._containerElement.appendChild(d);let m=window.getComputedStyle(d),A=m.transitionProperty,M=m.transitionDuration,D=A==="none"||M==="0s"||M==="0s, 0s"||o.width===0&&o.height===0,R=new Ke(this,d,e,D);d.style.transform="scale3d(1, 1, 1)",R.state=z.FADING_IN,e.persistent||(this._mostRecentTransientRipple=R);let J=null;return!D&&(u||r.exitDuration)&&this._ngZone.runOutsideAngular(()=>{let dn=()=>{J&&(J.fallbackTimer=null),clearTimeout(un),this._finishRippleTransition(R)},Se=()=>this._destroyRipple(R),un=setTimeout(Se,u+100);d.addEventListener("transitionend",dn),d.addEventListener("transitioncancel",Se),J={onTransitionEnd:dn,onTransitionCancel:Se,fallbackTimer:un}}),this._activeRipples.set(R,J),(D||!u)&&this._finishRippleTransition(R),R}fadeOutRipple(n){if(n.state===z.FADING_OUT||n.state===z.HIDDEN)return;let t=n.element,e=x(x({},Pt),n.config.animation);t.style.transitionDuration=`${e.exitDuration}ms`,t.style.opacity="0",n.state=z.FADING_OUT,(n._animationForciblyDisabledThroughCss||!e.exitDuration)&&this._finishRippleTransition(n)}fadeOutAll(){this._getActiveRipples().forEach(n=>n.fadeOut())}fadeOutAllNonPersistent(){this._getActiveRipples().forEach(n=>{n.config.persistent||n.fadeOut()})}setupTriggerEvents(n){let t=K(n);!this._platform.isBrowser||!t||t===this._triggerElement||(this._removeTriggerEvents(),this._triggerElement=t,Si.forEach(e=>{i._eventManager.addHandler(this._ngZone,e,t,this)}))}handleEvent(n){n.type==="mousedown"?this._onMousedown(n):n.type==="touchstart"?this._onTouchStart(n):this._onPointerUp(),this._pointerUpEventsRegistered||(this._ngZone.runOutsideAngular(()=>{Ii.forEach(t=>{this._triggerElement.addEventListener(t,this,Ci)})}),this._pointerUpEventsRegistered=!0)}_finishRippleTransition(n){n.state===z.FADING_IN?this._startFadeOutTransition(n):n.state===z.FADING_OUT&&this._destroyRipple(n)}_startFadeOutTransition(n){let t=n===this._mostRecentTransientRipple,{persistent:e}=n.config;n.state=z.VISIBLE,!e&&(!t||!this._isPointerDown)&&n.fadeOut()}_destroyRipple(n){let t=this._activeRipples.get(n)??null;this._activeRipples.delete(n),this._activeRipples.size||(this._containerRect=null),n===this._mostRecentTransientRipple&&(this._mostRecentTransientRipple=null),n.state=z.HIDDEN,t!==null&&(n.element.removeEventListener("transitionend",t.onTransitionEnd),n.element.removeEventListener("transitioncancel",t.onTransitionCancel),t.fallbackTimer!==null&&clearTimeout(t.fallbackTimer)),n.element.remove()}_onMousedown(n){let t=Dt(n),e=this._lastTouchStartEvent&&Date.now()<this._lastTouchStartEvent+Ko;!this._target.rippleDisabled&&!t&&!e&&(this._isPointerDown=!0,this.fadeInRipple(n.clientX,n.clientY,this._target.rippleConfig))}_onTouchStart(n){if(!this._target.rippleDisabled&&!Ot(n)){this._lastTouchStartEvent=Date.now(),this._isPointerDown=!0;let t=n.changedTouches;if(t)for(let e=0;e<t.length;e++)this.fadeInRipple(t[e].clientX,t[e].clientY,this._target.rippleConfig)}}_onPointerUp(){this._isPointerDown&&(this._isPointerDown=!1,this._getActiveRipples().forEach(n=>{let t=n.state===z.VISIBLE||n.config.terminateOnPointerUp&&n.state===z.FADING_IN;!n.config.persistent&&t&&n.fadeOut()}))}_getActiveRipples(){return Array.from(this._activeRipples.keys())}_removeTriggerEvents(){let n=this._triggerElement;n&&(Si.forEach(t=>i._eventManager.removeHandler(t,n,this)),this._pointerUpEventsRegistered&&(Ii.forEach(t=>n.removeEventListener(t,this,Ci)),this._pointerUpEventsRegistered=!1))}};function $o(i,n,t){let e=Math.max(Math.abs(i-t.left),Math.abs(i-t.right)),o=Math.max(Math.abs(n-t.top),Math.abs(n-t.bottom));return Math.sqrt(e*e+o*o)}var $e=new p("mat-ripple-global-options"),Fl=(()=>{class i{_elementRef=a(v);_animationsDisabled=gt();color;unbounded=!1;centered=!1;radius=0;animation;get disabled(){return this._disabled}set disabled(t){t&&this.fadeOutAllNonPersistent(),this._disabled=t,this._setupTriggerEventsIfEnabled()}_disabled=!1;get trigger(){return this._trigger||this._elementRef.nativeElement}set trigger(t){this._trigger=t,this._setupTriggerEventsIfEnabled()}_trigger;_rippleRenderer;_globalOptions;_isInitialized=!1;constructor(){let t=a(_),e=a(g),o=a($e,{optional:!0}),r=a(S);this._globalOptions=o||{},this._rippleRenderer=new Ft(this,t,this._elementRef,e,r)}ngOnInit(){this._isInitialized=!0,this._setupTriggerEventsIfEnabled()}ngOnDestroy(){this._rippleRenderer._removeTriggerEvents()}fadeOutAll(){this._rippleRenderer.fadeOutAll()}fadeOutAllNonPersistent(){this._rippleRenderer.fadeOutAllNonPersistent()}get rippleConfig(){return{centered:this.centered,radius:this.radius,color:this.color,animation:x(x(x({},this._globalOptions.animation),this._animationsDisabled?{enterDuration:0,exitDuration:0}:{}),this.animation),terminateOnPointerUp:this._globalOptions.terminateOnPointerUp}}get rippleDisabled(){return this.disabled||!!this._globalOptions.disabled}_setupTriggerEventsIfEnabled(){!this.disabled&&this._isInitialized&&this._rippleRenderer.setupTriggerEvents(this.trigger)}launch(t,e=0,o){return typeof t=="number"?this._rippleRenderer.fadeInRipple(t,e,x(x({},this.rippleConfig),o)):this._rippleRenderer.fadeInRipple(0,0,x(x({},this.rippleConfig),t))}static \u0275fac=function(e){return new(e||i)};static \u0275dir=k({type:i,selectors:[["","mat-ripple",""],["","matRipple",""]],hostAttrs:[1,"mat-ripple"],hostVars:2,hostBindings:function(e,o){e&2&&L("mat-ripple-unbounded",o.unbounded)},inputs:{color:[0,"matRippleColor","color"],unbounded:[0,"matRippleUnbounded","unbounded"],centered:[0,"matRippleCentered","centered"],radius:[0,"matRippleRadius","radius"],animation:[0,"matRippleAnimation","animation"],disabled:[0,"matRippleDisabled","disabled"],trigger:[0,"matRippleTrigger","trigger"]},exportAs:["matRipple"]})}return i})();var Go={capture:!0},qo=["focus","mousedown","mouseenter","touchstart"],Ge="mat-ripple-loader-uninitialized",qe="mat-ripple-loader-class-name",ki="mat-ripple-loader-centered",pe="mat-ripple-loader-disabled",Ri=(()=>{class i{_document=a(b);_animationsDisabled=gt();_globalRippleOptions=a($e,{optional:!0});_platform=a(g);_ngZone=a(_);_injector=a(S);_eventCleanups;_hosts=new Map;constructor(){let t=a(j).createRenderer(null,null);this._eventCleanups=this._ngZone.runOutsideAngular(()=>qo.map(e=>t.listen(this._document,e,this._onInteraction,Go)))}ngOnDestroy(){let t=this._hosts.keys();for(let e of t)this.destroyRipple(e);this._eventCleanups.forEach(e=>e())}configureRipple(t,e){t.setAttribute(Ge,this._globalRippleOptions?.namespace??""),(e.className||!t.hasAttribute(qe))&&t.setAttribute(qe,e.className||""),e.centered&&t.setAttribute(ki,""),e.disabled&&t.setAttribute(pe,"")}setDisabled(t,e){let o=this._hosts.get(t);o?(o.target.rippleDisabled=e,!e&&!o.hasSetUpEvents&&(o.hasSetUpEvents=!0,o.renderer.setupTriggerEvents(t))):e?t.setAttribute(pe,""):t.removeAttribute(pe)}_onInteraction=t=>{let e=T(t);if(e instanceof HTMLElement){let o=e.closest(`[${Ge}="${this._globalRippleOptions?.namespace??""}"]`);o&&this._createRipple(o)}};_createRipple(t){if(!this._document||this._hosts.has(t))return;t.querySelector(".mat-ripple")?.remove();let e=this._document.createElement("span");e.classList.add("mat-ripple",t.getAttribute(qe)),t.append(e);let o=this._globalRippleOptions,r=this._animationsDisabled?0:o?.animation?.enterDuration??Pt.enterDuration,s=this._animationsDisabled?0:o?.animation?.exitDuration??Pt.exitDuration,l={rippleDisabled:this._animationsDisabled||o?.disabled||t.hasAttribute(pe),rippleConfig:{centered:t.hasAttribute(ki),terminateOnPointerUp:o?.terminateOnPointerUp,animation:{enterDuration:r,exitDuration:s}}},c=new Ft(l,this._ngZone,e,this._platform,this._injector),u=!l.rippleDisabled;u&&c.setupTriggerEvents(t),this._hosts.set(t,{target:l,renderer:c,hasSetUpEvents:u}),t.removeAttribute(Ge)}destroyRipple(t){let e=this._hosts.get(t);e&&(e.renderer._removeTriggerEvents(),this._hosts.delete(t))}static \u0275fac=function(e){return new(e||i)};static \u0275prov=h({token:i,factory:i.\u0275fac,providedIn:"root"})}return i})();var Di=(()=>{class i{static \u0275fac=function(e){return new(e||i)};static \u0275cmp=O({type:i,selectors:[["structural-styles"]],decls:0,vars:0,template:function(e,o){},styles:[`.mat-focus-indicator {
  position: relative;
}
.mat-focus-indicator::before {
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  position: absolute;
  box-sizing: border-box;
  pointer-events: none;
  display: var(--mat-focus-indicator-display, none);
  border-width: var(--mat-focus-indicator-border-width, 3px);
  border-style: var(--mat-focus-indicator-border-style, solid);
  border-color: var(--mat-focus-indicator-border-color, transparent);
  border-radius: var(--mat-focus-indicator-border-radius, 4px);
}
.mat-focus-indicator:focus-visible::before {
  content: "";
}

@media (forced-colors: active) {
  html {
    --mat-focus-indicator-display: block;
  }
}
`],encapsulation:2,changeDetection:0})}return i})();var Qo=["mat-icon-button",""],Jo=["*"],tr=new p("MAT_BUTTON_CONFIG");function Oi(i){return i==null?void 0:Ln(i)}var fe=(()=>{class i{_elementRef=a(v);_ngZone=a(_);_animationsDisabled=gt();_config=a(tr,{optional:!0});_focusMonitor=a(ae);_cleanupClick;_renderer=a(ht);_rippleLoader=a(Ri);_isAnchor;_isFab=!1;color;get disableRipple(){return this._disableRipple}set disableRipple(t){this._disableRipple=t,this._updateRippleDisabled()}_disableRipple=!1;get disabled(){return this._disabled}set disabled(t){this._disabled=t,this._updateRippleDisabled()}_disabled=!1;ariaDisabled;disabledInteractive;tabIndex;set _tabindex(t){this.tabIndex=t}constructor(){a(B).load(Di);let t=this._elementRef.nativeElement;this._isAnchor=t.tagName==="A",this.disabledInteractive=this._config?.disabledInteractive??!1,this.color=this._config?.color??null,this._rippleLoader?.configureRipple(t,{className:"mat-mdc-button-ripple"})}ngAfterViewInit(){this._focusMonitor.monitor(this._elementRef,!0),this._isAnchor&&this._setupAsAnchor()}ngOnDestroy(){this._cleanupClick?.(),this._focusMonitor.stopMonitoring(this._elementRef),this._rippleLoader?.destroyRipple(this._elementRef.nativeElement)}focus(t="program",e){t?this._focusMonitor.focusVia(this._elementRef.nativeElement,t,e):this._elementRef.nativeElement.focus(e)}_getAriaDisabled(){return this.ariaDisabled!=null?this.ariaDisabled:this._isAnchor?this.disabled||null:this.disabled&&this.disabledInteractive?!0:null}_getDisabledAttribute(){return this.disabledInteractive||!this.disabled?null:!0}_updateRippleDisabled(){this._rippleLoader?.setDisabled(this._elementRef.nativeElement,this.disableRipple||this.disabled)}_getTabIndex(){return this._isAnchor?this.disabled&&!this.disabledInteractive?-1:this.tabIndex:this.tabIndex}_setupAsAnchor(){this._cleanupClick=this._ngZone.runOutsideAngular(()=>this._renderer.listen(this._elementRef.nativeElement,"click",t=>{this.disabled&&(t.preventDefault(),t.stopImmediatePropagation())}))}static \u0275fac=function(e){return new(e||i)};static \u0275dir=k({type:i,hostAttrs:[1,"mat-mdc-button-base"],hostVars:13,hostBindings:function(e,o){e&2&&(xt("disabled",o._getDisabledAttribute())("aria-disabled",o._getAriaDisabled())("tabindex",o._getTabIndex()),Gt(o.color?"mat-"+o.color:""),L("mat-mdc-button-disabled",o.disabled)("mat-mdc-button-disabled-interactive",o.disabledInteractive)("mat-unthemed",!o.color)("_mat-animation-noopable",o._animationsDisabled))},inputs:{color:"color",disableRipple:[2,"disableRipple","disableRipple",y],disabled:[2,"disabled","disabled",y],ariaDisabled:[2,"aria-disabled","ariaDisabled",y],disabledInteractive:[2,"disabledInteractive","disabledInteractive",y],tabIndex:[2,"tabIndex","tabIndex",Oi],_tabindex:[2,"tabindex","_tabindex",Oi]}})}return i})(),er=(()=>{class i extends fe{constructor(){super(),this._rippleLoader.configureRipple(this._elementRef.nativeElement,{centered:!0})}static \u0275fac=function(e){return new(e||i)};static \u0275cmp=O({type:i,selectors:[["button","mat-icon-button",""],["a","mat-icon-button",""],["button","matIconButton",""],["a","matIconButton",""]],hostAttrs:[1,"mdc-icon-button","mat-mdc-icon-button"],exportAs:["matButton","matAnchor"],features:[Y],attrs:Qo,ngContentSelectors:Jo,decls:4,vars:0,consts:[[1,"mat-mdc-button-persistent-ripple","mdc-icon-button__ripple"],[1,"mat-focus-indicator"],[1,"mat-mdc-button-touch-target"]],template:function(e,o){e&1&&($(),X(0,"span",0),N(1),X(2,"span",1)(3,"span",2))},styles:[`.mat-mdc-icon-button {
  -webkit-user-select: none;
  user-select: none;
  display: inline-block;
  position: relative;
  box-sizing: border-box;
  border: none;
  outline: none;
  background-color: transparent;
  fill: currentColor;
  text-decoration: none;
  cursor: pointer;
  z-index: 0;
  overflow: visible;
  border-radius: var(--mat-icon-button-container-shape, var(--mat-sys-corner-full, 50%));
  flex-shrink: 0;
  text-align: center;
  width: var(--mat-icon-button-state-layer-size, 40px);
  height: var(--mat-icon-button-state-layer-size, 40px);
  padding: calc(calc(var(--mat-icon-button-state-layer-size, 40px) - var(--mat-icon-button-icon-size, 24px)) / 2);
  font-size: var(--mat-icon-button-icon-size, 24px);
  color: var(--mat-icon-button-icon-color, var(--mat-sys-on-surface-variant));
  -webkit-tap-highlight-color: transparent;
}
.mat-mdc-icon-button .mat-mdc-button-ripple,
.mat-mdc-icon-button .mat-mdc-button-persistent-ripple,
.mat-mdc-icon-button .mat-mdc-button-persistent-ripple::before {
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  position: absolute;
  pointer-events: none;
  border-radius: inherit;
}
.mat-mdc-icon-button .mat-mdc-button-ripple {
  overflow: hidden;
}
.mat-mdc-icon-button .mat-mdc-button-persistent-ripple::before {
  content: "";
  opacity: 0;
}
.mat-mdc-icon-button .mdc-button__label,
.mat-mdc-icon-button .mat-icon {
  z-index: 1;
  position: relative;
}
.mat-mdc-icon-button .mat-focus-indicator {
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  position: absolute;
  border-radius: inherit;
}
.mat-mdc-icon-button:focus-visible > .mat-focus-indicator::before {
  content: "";
  border-radius: inherit;
}
.mat-mdc-icon-button .mat-ripple-element {
  background-color: var(--mat-icon-button-ripple-color, color-mix(in srgb, var(--mat-sys-on-surface-variant) calc(var(--mat-sys-pressed-state-layer-opacity) * 100%), transparent));
}
.mat-mdc-icon-button .mat-mdc-button-persistent-ripple::before {
  background-color: var(--mat-icon-button-state-layer-color, var(--mat-sys-on-surface-variant));
}
.mat-mdc-icon-button.mat-mdc-button-disabled .mat-mdc-button-persistent-ripple::before {
  background-color: var(--mat-icon-button-disabled-state-layer-color, var(--mat-sys-on-surface-variant));
}
.mat-mdc-icon-button:hover > .mat-mdc-button-persistent-ripple::before {
  opacity: var(--mat-icon-button-hover-state-layer-opacity, var(--mat-sys-hover-state-layer-opacity));
}
.mat-mdc-icon-button.cdk-program-focused > .mat-mdc-button-persistent-ripple::before, .mat-mdc-icon-button.cdk-keyboard-focused > .mat-mdc-button-persistent-ripple::before, .mat-mdc-icon-button.mat-mdc-button-disabled-interactive:focus > .mat-mdc-button-persistent-ripple::before {
  opacity: var(--mat-icon-button-focus-state-layer-opacity, var(--mat-sys-focus-state-layer-opacity));
}
.mat-mdc-icon-button:active > .mat-mdc-button-persistent-ripple::before {
  opacity: var(--mat-icon-button-pressed-state-layer-opacity, var(--mat-sys-pressed-state-layer-opacity));
}
.mat-mdc-icon-button .mat-mdc-button-touch-target {
  position: absolute;
  top: 50%;
  height: var(--mat-icon-button-touch-target-size, 48px);
  display: var(--mat-icon-button-touch-target-display, block);
  left: 50%;
  width: var(--mat-icon-button-touch-target-size, 48px);
  transform: translate(-50%, -50%);
}
.mat-mdc-icon-button._mat-animation-noopable {
  transition: none !important;
  animation: none !important;
}
.mat-mdc-icon-button[disabled], .mat-mdc-icon-button.mat-mdc-button-disabled {
  cursor: default;
  pointer-events: none;
  color: var(--mat-icon-button-disabled-icon-color, color-mix(in srgb, var(--mat-sys-on-surface) 38%, transparent));
}
.mat-mdc-icon-button.mat-mdc-button-disabled-interactive {
  pointer-events: auto;
}
.mat-mdc-icon-button img,
.mat-mdc-icon-button svg {
  width: var(--mat-icon-button-icon-size, 24px);
  height: var(--mat-icon-button-icon-size, 24px);
  vertical-align: baseline;
}
.mat-mdc-icon-button .mat-mdc-button-persistent-ripple {
  border-radius: var(--mat-icon-button-container-shape, var(--mat-sys-corner-full, 50%));
}
.mat-mdc-icon-button[hidden] {
  display: none;
}
.mat-mdc-icon-button.mat-unthemed:not(.mdc-ripple-upgraded):focus::before, .mat-mdc-icon-button.mat-primary:not(.mdc-ripple-upgraded):focus::before, .mat-mdc-icon-button.mat-accent:not(.mdc-ripple-upgraded):focus::before, .mat-mdc-icon-button.mat-warn:not(.mdc-ripple-upgraded):focus::before {
  background: transparent;
  opacity: 1;
}
`,`@media (forced-colors: active) {
  .mat-mdc-button:not(.mdc-button--outlined),
  .mat-mdc-unelevated-button:not(.mdc-button--outlined),
  .mat-mdc-raised-button:not(.mdc-button--outlined),
  .mat-mdc-outlined-button:not(.mdc-button--outlined),
  .mat-mdc-button-base.mat-tonal-button,
  .mat-mdc-icon-button.mat-mdc-icon-button,
  .mat-mdc-outlined-button .mdc-button__ripple {
    outline: solid 1px;
  }
}
`],encapsulation:2,changeDetection:0})}return i})();var Ai=(()=>{class i{static \u0275fac=function(e){return new(e||i)};static \u0275mod=I({type:i});static \u0275inj=C({imports:[U]})}return i})();var nr=["matButton",""],Ti=[[["",8,"material-icons",3,"iconPositionEnd",""],["mat-icon",3,"iconPositionEnd",""],["","matButtonIcon","",3,"iconPositionEnd",""]],"*",[["","iconPositionEnd","",8,"material-icons"],["mat-icon","iconPositionEnd",""],["","matButtonIcon","","iconPositionEnd",""]]],Pi=[".material-icons:not([iconPositionEnd]), mat-icon:not([iconPositionEnd]), [matButtonIcon]:not([iconPositionEnd])","*",".material-icons[iconPositionEnd], mat-icon[iconPositionEnd], [matButtonIcon][iconPositionEnd]"],ir=["mat-fab",""];var Mi=new Map([["text",["mat-mdc-button"]],["filled",["mdc-button--unelevated","mat-mdc-unelevated-button"]],["elevated",["mdc-button--raised","mat-mdc-raised-button"]],["outlined",["mdc-button--outlined","mat-mdc-outlined-button"]],["tonal",["mat-tonal-button"]]]),pc=(()=>{class i extends fe{get appearance(){return this._appearance}set appearance(t){this.setAppearance(t||this._config?.defaultAppearance||"text")}_appearance=null;constructor(){super();let t=or(this._elementRef.nativeElement);t&&this.setAppearance(t)}setAppearance(t){if(t===this._appearance)return;let e=this._elementRef.nativeElement.classList,o=this._appearance?Mi.get(this._appearance):null,r=Mi.get(t);o&&e.remove(...o),e.add(...r),this._appearance=t}static \u0275fac=function(e){return new(e||i)};static \u0275cmp=O({type:i,selectors:[["button","matButton",""],["a","matButton",""],["button","mat-button",""],["button","mat-raised-button",""],["button","mat-flat-button",""],["button","mat-stroked-button",""],["a","mat-button",""],["a","mat-raised-button",""],["a","mat-flat-button",""],["a","mat-stroked-button",""]],hostAttrs:[1,"mdc-button"],inputs:{appearance:[0,"matButton","appearance"]},exportAs:["matButton","matAnchor"],features:[Y],attrs:nr,ngContentSelectors:Pi,decls:7,vars:4,consts:[[1,"mat-mdc-button-persistent-ripple"],[1,"mdc-button__label"],[1,"mat-focus-indicator"],[1,"mat-mdc-button-touch-target"]],template:function(e,o){e&1&&($(Ti),X(0,"span",0),N(1),Ct(2,"span",1),N(3,1),St(),N(4,2),X(5,"span",2)(6,"span",3)),e&2&&L("mdc-button__ripple",!o._isFab)("mdc-fab__ripple",o._isFab)},styles:[`.mat-mdc-button-base {
  text-decoration: none;
}
.mat-mdc-button-base .mat-icon {
  min-height: fit-content;
  flex-shrink: 0;
}
@media (hover: none) {
  .mat-mdc-button-base:hover > span.mat-mdc-button-persistent-ripple::before {
    opacity: 0;
  }
}

.mdc-button {
  -webkit-user-select: none;
  user-select: none;
  position: relative;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  box-sizing: border-box;
  min-width: 64px;
  border: none;
  outline: none;
  line-height: inherit;
  -webkit-appearance: none;
  overflow: visible;
  vertical-align: middle;
  background: transparent;
  padding: 0 8px;
}
.mdc-button::-moz-focus-inner {
  padding: 0;
  border: 0;
}
.mdc-button:active {
  outline: none;
}
.mdc-button:hover {
  cursor: pointer;
}
.mdc-button:disabled {
  cursor: default;
  pointer-events: none;
}
.mdc-button[hidden] {
  display: none;
}
.mdc-button .mdc-button__label {
  position: relative;
}

.mat-mdc-button {
  padding: 0 var(--mat-button-text-horizontal-padding, 12px);
  height: var(--mat-button-text-container-height, 40px);
  font-family: var(--mat-button-text-label-text-font, var(--mat-sys-label-large-font));
  font-size: var(--mat-button-text-label-text-size, var(--mat-sys-label-large-size));
  letter-spacing: var(--mat-button-text-label-text-tracking, var(--mat-sys-label-large-tracking));
  text-transform: var(--mat-button-text-label-text-transform);
  font-weight: var(--mat-button-text-label-text-weight, var(--mat-sys-label-large-weight));
}
.mat-mdc-button, .mat-mdc-button .mdc-button__ripple {
  border-radius: var(--mat-button-text-container-shape, var(--mat-sys-corner-full));
}
.mat-mdc-button:not(:disabled) {
  color: var(--mat-button-text-label-text-color, var(--mat-sys-primary));
}
.mat-mdc-button[disabled], .mat-mdc-button.mat-mdc-button-disabled {
  cursor: default;
  pointer-events: none;
  color: var(--mat-button-text-disabled-label-text-color, color-mix(in srgb, var(--mat-sys-on-surface) 38%, transparent));
}
.mat-mdc-button.mat-mdc-button-disabled-interactive {
  pointer-events: auto;
}
.mat-mdc-button:has(.material-icons, mat-icon, [matButtonIcon]) {
  padding: 0 var(--mat-button-text-with-icon-horizontal-padding, 16px);
}
.mat-mdc-button > .mat-icon {
  margin-right: var(--mat-button-text-icon-spacing, 8px);
  margin-left: var(--mat-button-text-icon-offset, -4px);
}
[dir=rtl] .mat-mdc-button > .mat-icon {
  margin-right: var(--mat-button-text-icon-offset, -4px);
  margin-left: var(--mat-button-text-icon-spacing, 8px);
}
.mat-mdc-button .mdc-button__label + .mat-icon {
  margin-right: var(--mat-button-text-icon-offset, -4px);
  margin-left: var(--mat-button-text-icon-spacing, 8px);
}
[dir=rtl] .mat-mdc-button .mdc-button__label + .mat-icon {
  margin-right: var(--mat-button-text-icon-spacing, 8px);
  margin-left: var(--mat-button-text-icon-offset, -4px);
}
.mat-mdc-button .mat-ripple-element {
  background-color: var(--mat-button-text-ripple-color, color-mix(in srgb, var(--mat-sys-primary) calc(var(--mat-sys-pressed-state-layer-opacity) * 100%), transparent));
}
.mat-mdc-button .mat-mdc-button-persistent-ripple::before {
  background-color: var(--mat-button-text-state-layer-color, var(--mat-sys-primary));
}
.mat-mdc-button.mat-mdc-button-disabled .mat-mdc-button-persistent-ripple::before {
  background-color: var(--mat-button-text-disabled-state-layer-color, var(--mat-sys-on-surface-variant));
}
.mat-mdc-button:hover > .mat-mdc-button-persistent-ripple::before {
  opacity: var(--mat-button-text-hover-state-layer-opacity, var(--mat-sys-hover-state-layer-opacity));
}
.mat-mdc-button.cdk-program-focused > .mat-mdc-button-persistent-ripple::before, .mat-mdc-button.cdk-keyboard-focused > .mat-mdc-button-persistent-ripple::before, .mat-mdc-button.mat-mdc-button-disabled-interactive:focus > .mat-mdc-button-persistent-ripple::before {
  opacity: var(--mat-button-text-focus-state-layer-opacity, var(--mat-sys-focus-state-layer-opacity));
}
.mat-mdc-button:active > .mat-mdc-button-persistent-ripple::before {
  opacity: var(--mat-button-text-pressed-state-layer-opacity, var(--mat-sys-pressed-state-layer-opacity));
}
.mat-mdc-button .mat-mdc-button-touch-target {
  position: absolute;
  top: 50%;
  height: var(--mat-button-text-touch-target-size, 48px);
  display: var(--mat-button-text-touch-target-display, block);
  left: 0;
  right: 0;
  transform: translateY(-50%);
}

.mat-mdc-unelevated-button {
  transition: box-shadow 280ms cubic-bezier(0.4, 0, 0.2, 1);
  height: var(--mat-button-filled-container-height, 40px);
  font-family: var(--mat-button-filled-label-text-font, var(--mat-sys-label-large-font));
  font-size: var(--mat-button-filled-label-text-size, var(--mat-sys-label-large-size));
  letter-spacing: var(--mat-button-filled-label-text-tracking, var(--mat-sys-label-large-tracking));
  text-transform: var(--mat-button-filled-label-text-transform);
  font-weight: var(--mat-button-filled-label-text-weight, var(--mat-sys-label-large-weight));
  padding: 0 var(--mat-button-filled-horizontal-padding, 24px);
}
.mat-mdc-unelevated-button > .mat-icon {
  margin-right: var(--mat-button-filled-icon-spacing, 8px);
  margin-left: var(--mat-button-filled-icon-offset, -8px);
}
[dir=rtl] .mat-mdc-unelevated-button > .mat-icon {
  margin-right: var(--mat-button-filled-icon-offset, -8px);
  margin-left: var(--mat-button-filled-icon-spacing, 8px);
}
.mat-mdc-unelevated-button .mdc-button__label + .mat-icon {
  margin-right: var(--mat-button-filled-icon-offset, -8px);
  margin-left: var(--mat-button-filled-icon-spacing, 8px);
}
[dir=rtl] .mat-mdc-unelevated-button .mdc-button__label + .mat-icon {
  margin-right: var(--mat-button-filled-icon-spacing, 8px);
  margin-left: var(--mat-button-filled-icon-offset, -8px);
}
.mat-mdc-unelevated-button .mat-ripple-element {
  background-color: var(--mat-button-filled-ripple-color, color-mix(in srgb, var(--mat-sys-on-primary) calc(var(--mat-sys-pressed-state-layer-opacity) * 100%), transparent));
}
.mat-mdc-unelevated-button .mat-mdc-button-persistent-ripple::before {
  background-color: var(--mat-button-filled-state-layer-color, var(--mat-sys-on-primary));
}
.mat-mdc-unelevated-button.mat-mdc-button-disabled .mat-mdc-button-persistent-ripple::before {
  background-color: var(--mat-button-filled-disabled-state-layer-color, var(--mat-sys-on-surface-variant));
}
.mat-mdc-unelevated-button:hover > .mat-mdc-button-persistent-ripple::before {
  opacity: var(--mat-button-filled-hover-state-layer-opacity, var(--mat-sys-hover-state-layer-opacity));
}
.mat-mdc-unelevated-button.cdk-program-focused > .mat-mdc-button-persistent-ripple::before, .mat-mdc-unelevated-button.cdk-keyboard-focused > .mat-mdc-button-persistent-ripple::before, .mat-mdc-unelevated-button.mat-mdc-button-disabled-interactive:focus > .mat-mdc-button-persistent-ripple::before {
  opacity: var(--mat-button-filled-focus-state-layer-opacity, var(--mat-sys-focus-state-layer-opacity));
}
.mat-mdc-unelevated-button:active > .mat-mdc-button-persistent-ripple::before {
  opacity: var(--mat-button-filled-pressed-state-layer-opacity, var(--mat-sys-pressed-state-layer-opacity));
}
.mat-mdc-unelevated-button .mat-mdc-button-touch-target {
  position: absolute;
  top: 50%;
  height: var(--mat-button-filled-touch-target-size, 48px);
  display: var(--mat-button-filled-touch-target-display, block);
  left: 0;
  right: 0;
  transform: translateY(-50%);
}
.mat-mdc-unelevated-button:not(:disabled) {
  color: var(--mat-button-filled-label-text-color, var(--mat-sys-on-primary));
  background-color: var(--mat-button-filled-container-color, var(--mat-sys-primary));
}
.mat-mdc-unelevated-button, .mat-mdc-unelevated-button .mdc-button__ripple {
  border-radius: var(--mat-button-filled-container-shape, var(--mat-sys-corner-full));
}
.mat-mdc-unelevated-button[disabled], .mat-mdc-unelevated-button.mat-mdc-button-disabled {
  cursor: default;
  pointer-events: none;
  color: var(--mat-button-filled-disabled-label-text-color, color-mix(in srgb, var(--mat-sys-on-surface) 38%, transparent));
  background-color: var(--mat-button-filled-disabled-container-color, color-mix(in srgb, var(--mat-sys-on-surface) 12%, transparent));
}
.mat-mdc-unelevated-button.mat-mdc-button-disabled-interactive {
  pointer-events: auto;
}

.mat-mdc-raised-button {
  transition: box-shadow 280ms cubic-bezier(0.4, 0, 0.2, 1);
  box-shadow: var(--mat-button-protected-container-elevation-shadow, var(--mat-sys-level1));
  height: var(--mat-button-protected-container-height, 40px);
  font-family: var(--mat-button-protected-label-text-font, var(--mat-sys-label-large-font));
  font-size: var(--mat-button-protected-label-text-size, var(--mat-sys-label-large-size));
  letter-spacing: var(--mat-button-protected-label-text-tracking, var(--mat-sys-label-large-tracking));
  text-transform: var(--mat-button-protected-label-text-transform);
  font-weight: var(--mat-button-protected-label-text-weight, var(--mat-sys-label-large-weight));
  padding: 0 var(--mat-button-protected-horizontal-padding, 24px);
}
.mat-mdc-raised-button > .mat-icon {
  margin-right: var(--mat-button-protected-icon-spacing, 8px);
  margin-left: var(--mat-button-protected-icon-offset, -8px);
}
[dir=rtl] .mat-mdc-raised-button > .mat-icon {
  margin-right: var(--mat-button-protected-icon-offset, -8px);
  margin-left: var(--mat-button-protected-icon-spacing, 8px);
}
.mat-mdc-raised-button .mdc-button__label + .mat-icon {
  margin-right: var(--mat-button-protected-icon-offset, -8px);
  margin-left: var(--mat-button-protected-icon-spacing, 8px);
}
[dir=rtl] .mat-mdc-raised-button .mdc-button__label + .mat-icon {
  margin-right: var(--mat-button-protected-icon-spacing, 8px);
  margin-left: var(--mat-button-protected-icon-offset, -8px);
}
.mat-mdc-raised-button .mat-ripple-element {
  background-color: var(--mat-button-protected-ripple-color, color-mix(in srgb, var(--mat-sys-primary) calc(var(--mat-sys-pressed-state-layer-opacity) * 100%), transparent));
}
.mat-mdc-raised-button .mat-mdc-button-persistent-ripple::before {
  background-color: var(--mat-button-protected-state-layer-color, var(--mat-sys-primary));
}
.mat-mdc-raised-button.mat-mdc-button-disabled .mat-mdc-button-persistent-ripple::before {
  background-color: var(--mat-button-protected-disabled-state-layer-color, var(--mat-sys-on-surface-variant));
}
.mat-mdc-raised-button:hover > .mat-mdc-button-persistent-ripple::before {
  opacity: var(--mat-button-protected-hover-state-layer-opacity, var(--mat-sys-hover-state-layer-opacity));
}
.mat-mdc-raised-button.cdk-program-focused > .mat-mdc-button-persistent-ripple::before, .mat-mdc-raised-button.cdk-keyboard-focused > .mat-mdc-button-persistent-ripple::before, .mat-mdc-raised-button.mat-mdc-button-disabled-interactive:focus > .mat-mdc-button-persistent-ripple::before {
  opacity: var(--mat-button-protected-focus-state-layer-opacity, var(--mat-sys-focus-state-layer-opacity));
}
.mat-mdc-raised-button:active > .mat-mdc-button-persistent-ripple::before {
  opacity: var(--mat-button-protected-pressed-state-layer-opacity, var(--mat-sys-pressed-state-layer-opacity));
}
.mat-mdc-raised-button .mat-mdc-button-touch-target {
  position: absolute;
  top: 50%;
  height: var(--mat-button-protected-touch-target-size, 48px);
  display: var(--mat-button-protected-touch-target-display, block);
  left: 0;
  right: 0;
  transform: translateY(-50%);
}
.mat-mdc-raised-button:not(:disabled) {
  color: var(--mat-button-protected-label-text-color, var(--mat-sys-primary));
  background-color: var(--mat-button-protected-container-color, var(--mat-sys-surface));
}
.mat-mdc-raised-button, .mat-mdc-raised-button .mdc-button__ripple {
  border-radius: var(--mat-button-protected-container-shape, var(--mat-sys-corner-full));
}
@media (hover: hover) {
  .mat-mdc-raised-button:hover {
    box-shadow: var(--mat-button-protected-hover-container-elevation-shadow, var(--mat-sys-level2));
  }
}
.mat-mdc-raised-button:focus {
  box-shadow: var(--mat-button-protected-focus-container-elevation-shadow, var(--mat-sys-level1));
}
.mat-mdc-raised-button:active, .mat-mdc-raised-button:focus:active {
  box-shadow: var(--mat-button-protected-pressed-container-elevation-shadow, var(--mat-sys-level1));
}
.mat-mdc-raised-button[disabled], .mat-mdc-raised-button.mat-mdc-button-disabled {
  cursor: default;
  pointer-events: none;
  color: var(--mat-button-protected-disabled-label-text-color, color-mix(in srgb, var(--mat-sys-on-surface) 38%, transparent));
  background-color: var(--mat-button-protected-disabled-container-color, color-mix(in srgb, var(--mat-sys-on-surface) 12%, transparent));
}
.mat-mdc-raised-button[disabled].mat-mdc-button-disabled, .mat-mdc-raised-button.mat-mdc-button-disabled.mat-mdc-button-disabled {
  box-shadow: var(--mat-button-protected-disabled-container-elevation-shadow, var(--mat-sys-level0));
}
.mat-mdc-raised-button.mat-mdc-button-disabled-interactive {
  pointer-events: auto;
}

.mat-mdc-outlined-button {
  border-style: solid;
  transition: border 280ms cubic-bezier(0.4, 0, 0.2, 1);
  height: var(--mat-button-outlined-container-height, 40px);
  font-family: var(--mat-button-outlined-label-text-font, var(--mat-sys-label-large-font));
  font-size: var(--mat-button-outlined-label-text-size, var(--mat-sys-label-large-size));
  letter-spacing: var(--mat-button-outlined-label-text-tracking, var(--mat-sys-label-large-tracking));
  text-transform: var(--mat-button-outlined-label-text-transform);
  font-weight: var(--mat-button-outlined-label-text-weight, var(--mat-sys-label-large-weight));
  border-radius: var(--mat-button-outlined-container-shape, var(--mat-sys-corner-full));
  border-width: var(--mat-button-outlined-outline-width, 1px);
  padding: 0 var(--mat-button-outlined-horizontal-padding, 24px);
}
.mat-mdc-outlined-button > .mat-icon {
  margin-right: var(--mat-button-outlined-icon-spacing, 8px);
  margin-left: var(--mat-button-outlined-icon-offset, -8px);
}
[dir=rtl] .mat-mdc-outlined-button > .mat-icon {
  margin-right: var(--mat-button-outlined-icon-offset, -8px);
  margin-left: var(--mat-button-outlined-icon-spacing, 8px);
}
.mat-mdc-outlined-button .mdc-button__label + .mat-icon {
  margin-right: var(--mat-button-outlined-icon-offset, -8px);
  margin-left: var(--mat-button-outlined-icon-spacing, 8px);
}
[dir=rtl] .mat-mdc-outlined-button .mdc-button__label + .mat-icon {
  margin-right: var(--mat-button-outlined-icon-spacing, 8px);
  margin-left: var(--mat-button-outlined-icon-offset, -8px);
}
.mat-mdc-outlined-button .mat-ripple-element {
  background-color: var(--mat-button-outlined-ripple-color, color-mix(in srgb, var(--mat-sys-primary) calc(var(--mat-sys-pressed-state-layer-opacity) * 100%), transparent));
}
.mat-mdc-outlined-button .mat-mdc-button-persistent-ripple::before {
  background-color: var(--mat-button-outlined-state-layer-color, var(--mat-sys-primary));
}
.mat-mdc-outlined-button.mat-mdc-button-disabled .mat-mdc-button-persistent-ripple::before {
  background-color: var(--mat-button-outlined-disabled-state-layer-color, var(--mat-sys-on-surface-variant));
}
.mat-mdc-outlined-button:hover > .mat-mdc-button-persistent-ripple::before {
  opacity: var(--mat-button-outlined-hover-state-layer-opacity, var(--mat-sys-hover-state-layer-opacity));
}
.mat-mdc-outlined-button.cdk-program-focused > .mat-mdc-button-persistent-ripple::before, .mat-mdc-outlined-button.cdk-keyboard-focused > .mat-mdc-button-persistent-ripple::before, .mat-mdc-outlined-button.mat-mdc-button-disabled-interactive:focus > .mat-mdc-button-persistent-ripple::before {
  opacity: var(--mat-button-outlined-focus-state-layer-opacity, var(--mat-sys-focus-state-layer-opacity));
}
.mat-mdc-outlined-button:active > .mat-mdc-button-persistent-ripple::before {
  opacity: var(--mat-button-outlined-pressed-state-layer-opacity, var(--mat-sys-pressed-state-layer-opacity));
}
.mat-mdc-outlined-button .mat-mdc-button-touch-target {
  position: absolute;
  top: 50%;
  height: var(--mat-button-outlined-touch-target-size, 48px);
  display: var(--mat-button-outlined-touch-target-display, block);
  left: 0;
  right: 0;
  transform: translateY(-50%);
}
.mat-mdc-outlined-button:not(:disabled) {
  color: var(--mat-button-outlined-label-text-color, var(--mat-sys-primary));
  border-color: var(--mat-button-outlined-outline-color, var(--mat-sys-outline));
}
.mat-mdc-outlined-button[disabled], .mat-mdc-outlined-button.mat-mdc-button-disabled {
  cursor: default;
  pointer-events: none;
  color: var(--mat-button-outlined-disabled-label-text-color, color-mix(in srgb, var(--mat-sys-on-surface) 38%, transparent));
  border-color: var(--mat-button-outlined-disabled-outline-color, color-mix(in srgb, var(--mat-sys-on-surface) 12%, transparent));
}
.mat-mdc-outlined-button.mat-mdc-button-disabled-interactive {
  pointer-events: auto;
}

.mat-tonal-button {
  transition: box-shadow 280ms cubic-bezier(0.4, 0, 0.2, 1);
  height: var(--mat-button-tonal-container-height, 40px);
  font-family: var(--mat-button-tonal-label-text-font, var(--mat-sys-label-large-font));
  font-size: var(--mat-button-tonal-label-text-size, var(--mat-sys-label-large-size));
  letter-spacing: var(--mat-button-tonal-label-text-tracking, var(--mat-sys-label-large-tracking));
  text-transform: var(--mat-button-tonal-label-text-transform);
  font-weight: var(--mat-button-tonal-label-text-weight, var(--mat-sys-label-large-weight));
  padding: 0 var(--mat-button-tonal-horizontal-padding, 24px);
}
.mat-tonal-button:not(:disabled) {
  color: var(--mat-button-tonal-label-text-color, var(--mat-sys-on-secondary-container));
  background-color: var(--mat-button-tonal-container-color, var(--mat-sys-secondary-container));
}
.mat-tonal-button, .mat-tonal-button .mdc-button__ripple {
  border-radius: var(--mat-button-tonal-container-shape, var(--mat-sys-corner-full));
}
.mat-tonal-button[disabled], .mat-tonal-button.mat-mdc-button-disabled {
  cursor: default;
  pointer-events: none;
  color: var(--mat-button-tonal-disabled-label-text-color, color-mix(in srgb, var(--mat-sys-on-surface) 38%, transparent));
  background-color: var(--mat-button-tonal-disabled-container-color, color-mix(in srgb, var(--mat-sys-on-surface) 12%, transparent));
}
.mat-tonal-button.mat-mdc-button-disabled-interactive {
  pointer-events: auto;
}
.mat-tonal-button > .mat-icon {
  margin-right: var(--mat-button-tonal-icon-spacing, 8px);
  margin-left: var(--mat-button-tonal-icon-offset, -8px);
}
[dir=rtl] .mat-tonal-button > .mat-icon {
  margin-right: var(--mat-button-tonal-icon-offset, -8px);
  margin-left: var(--mat-button-tonal-icon-spacing, 8px);
}
.mat-tonal-button .mdc-button__label + .mat-icon {
  margin-right: var(--mat-button-tonal-icon-offset, -8px);
  margin-left: var(--mat-button-tonal-icon-spacing, 8px);
}
[dir=rtl] .mat-tonal-button .mdc-button__label + .mat-icon {
  margin-right: var(--mat-button-tonal-icon-spacing, 8px);
  margin-left: var(--mat-button-tonal-icon-offset, -8px);
}
.mat-tonal-button .mat-ripple-element {
  background-color: var(--mat-button-tonal-ripple-color, color-mix(in srgb, var(--mat-sys-on-secondary-container) calc(var(--mat-sys-pressed-state-layer-opacity) * 100%), transparent));
}
.mat-tonal-button .mat-mdc-button-persistent-ripple::before {
  background-color: var(--mat-button-tonal-state-layer-color, var(--mat-sys-on-secondary-container));
}
.mat-tonal-button.mat-mdc-button-disabled .mat-mdc-button-persistent-ripple::before {
  background-color: var(--mat-button-tonal-disabled-state-layer-color, var(--mat-sys-on-surface-variant));
}
.mat-tonal-button:hover > .mat-mdc-button-persistent-ripple::before {
  opacity: var(--mat-button-tonal-hover-state-layer-opacity, var(--mat-sys-hover-state-layer-opacity));
}
.mat-tonal-button.cdk-program-focused > .mat-mdc-button-persistent-ripple::before, .mat-tonal-button.cdk-keyboard-focused > .mat-mdc-button-persistent-ripple::before, .mat-tonal-button.mat-mdc-button-disabled-interactive:focus > .mat-mdc-button-persistent-ripple::before {
  opacity: var(--mat-button-tonal-focus-state-layer-opacity, var(--mat-sys-focus-state-layer-opacity));
}
.mat-tonal-button:active > .mat-mdc-button-persistent-ripple::before {
  opacity: var(--mat-button-tonal-pressed-state-layer-opacity, var(--mat-sys-pressed-state-layer-opacity));
}
.mat-tonal-button .mat-mdc-button-touch-target {
  position: absolute;
  top: 50%;
  height: var(--mat-button-tonal-touch-target-size, 48px);
  display: var(--mat-button-tonal-touch-target-display, block);
  left: 0;
  right: 0;
  transform: translateY(-50%);
}

.mat-mdc-button,
.mat-mdc-unelevated-button,
.mat-mdc-raised-button,
.mat-mdc-outlined-button,
.mat-tonal-button {
  -webkit-tap-highlight-color: transparent;
}
.mat-mdc-button .mat-mdc-button-ripple,
.mat-mdc-button .mat-mdc-button-persistent-ripple,
.mat-mdc-button .mat-mdc-button-persistent-ripple::before,
.mat-mdc-unelevated-button .mat-mdc-button-ripple,
.mat-mdc-unelevated-button .mat-mdc-button-persistent-ripple,
.mat-mdc-unelevated-button .mat-mdc-button-persistent-ripple::before,
.mat-mdc-raised-button .mat-mdc-button-ripple,
.mat-mdc-raised-button .mat-mdc-button-persistent-ripple,
.mat-mdc-raised-button .mat-mdc-button-persistent-ripple::before,
.mat-mdc-outlined-button .mat-mdc-button-ripple,
.mat-mdc-outlined-button .mat-mdc-button-persistent-ripple,
.mat-mdc-outlined-button .mat-mdc-button-persistent-ripple::before,
.mat-tonal-button .mat-mdc-button-ripple,
.mat-tonal-button .mat-mdc-button-persistent-ripple,
.mat-tonal-button .mat-mdc-button-persistent-ripple::before {
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  position: absolute;
  pointer-events: none;
  border-radius: inherit;
}
.mat-mdc-button .mat-mdc-button-ripple,
.mat-mdc-unelevated-button .mat-mdc-button-ripple,
.mat-mdc-raised-button .mat-mdc-button-ripple,
.mat-mdc-outlined-button .mat-mdc-button-ripple,
.mat-tonal-button .mat-mdc-button-ripple {
  overflow: hidden;
}
.mat-mdc-button .mat-mdc-button-persistent-ripple::before,
.mat-mdc-unelevated-button .mat-mdc-button-persistent-ripple::before,
.mat-mdc-raised-button .mat-mdc-button-persistent-ripple::before,
.mat-mdc-outlined-button .mat-mdc-button-persistent-ripple::before,
.mat-tonal-button .mat-mdc-button-persistent-ripple::before {
  content: "";
  opacity: 0;
}
.mat-mdc-button .mdc-button__label,
.mat-mdc-button .mat-icon,
.mat-mdc-unelevated-button .mdc-button__label,
.mat-mdc-unelevated-button .mat-icon,
.mat-mdc-raised-button .mdc-button__label,
.mat-mdc-raised-button .mat-icon,
.mat-mdc-outlined-button .mdc-button__label,
.mat-mdc-outlined-button .mat-icon,
.mat-tonal-button .mdc-button__label,
.mat-tonal-button .mat-icon {
  z-index: 1;
  position: relative;
}
.mat-mdc-button .mat-focus-indicator,
.mat-mdc-unelevated-button .mat-focus-indicator,
.mat-mdc-raised-button .mat-focus-indicator,
.mat-mdc-outlined-button .mat-focus-indicator,
.mat-tonal-button .mat-focus-indicator {
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  position: absolute;
  border-radius: inherit;
}
.mat-mdc-button:focus-visible > .mat-focus-indicator::before,
.mat-mdc-unelevated-button:focus-visible > .mat-focus-indicator::before,
.mat-mdc-raised-button:focus-visible > .mat-focus-indicator::before,
.mat-mdc-outlined-button:focus-visible > .mat-focus-indicator::before,
.mat-tonal-button:focus-visible > .mat-focus-indicator::before {
  content: "";
  border-radius: inherit;
}
.mat-mdc-button._mat-animation-noopable,
.mat-mdc-unelevated-button._mat-animation-noopable,
.mat-mdc-raised-button._mat-animation-noopable,
.mat-mdc-outlined-button._mat-animation-noopable,
.mat-tonal-button._mat-animation-noopable {
  transition: none !important;
  animation: none !important;
}
.mat-mdc-button > .mat-icon,
.mat-mdc-unelevated-button > .mat-icon,
.mat-mdc-raised-button > .mat-icon,
.mat-mdc-outlined-button > .mat-icon,
.mat-tonal-button > .mat-icon {
  display: inline-block;
  position: relative;
  vertical-align: top;
  font-size: 1.125rem;
  height: 1.125rem;
  width: 1.125rem;
}

.mat-mdc-outlined-button .mat-mdc-button-ripple,
.mat-mdc-outlined-button .mdc-button__ripple {
  top: -1px;
  left: -1px;
  bottom: -1px;
  right: -1px;
}

.mat-mdc-unelevated-button .mat-focus-indicator::before,
.mat-tonal-button .mat-focus-indicator::before,
.mat-mdc-raised-button .mat-focus-indicator::before {
  margin: calc(calc(var(--mat-focus-indicator-border-width, 3px) + 2px) * -1);
}

.mat-mdc-outlined-button .mat-focus-indicator::before {
  margin: calc(calc(var(--mat-focus-indicator-border-width, 3px) + 3px) * -1);
}
`,`@media (forced-colors: active) {
  .mat-mdc-button:not(.mdc-button--outlined),
  .mat-mdc-unelevated-button:not(.mdc-button--outlined),
  .mat-mdc-raised-button:not(.mdc-button--outlined),
  .mat-mdc-outlined-button:not(.mdc-button--outlined),
  .mat-mdc-button-base.mat-tonal-button,
  .mat-mdc-icon-button.mat-mdc-icon-button,
  .mat-mdc-outlined-button .mdc-button__ripple {
    outline: solid 1px;
  }
}
`],encapsulation:2,changeDetection:0})}return i})();function or(i){return i.hasAttribute("mat-raised-button")?"elevated":i.hasAttribute("mat-stroked-button")?"outlined":i.hasAttribute("mat-flat-button")?"filled":i.hasAttribute("mat-button")?"text":null}var rr=new p("mat-mdc-fab-default-options",{providedIn:"root",factory:()=>Qe}),Qe={color:"accent"},fc=(()=>{class i extends fe{_options=a(rr,{optional:!0});_isFab=!0;extended=!1;constructor(){super(),this._options=this._options||Qe,this.color=this._options.color||Qe.color}static \u0275fac=function(e){return new(e||i)};static \u0275cmp=O({type:i,selectors:[["button","mat-fab",""],["a","mat-fab",""],["button","matFab",""],["a","matFab",""]],hostAttrs:[1,"mdc-fab","mat-mdc-fab-base","mat-mdc-fab"],hostVars:4,hostBindings:function(e,o){e&2&&L("mdc-fab--extended",o.extended)("mat-mdc-extended-fab",o.extended)},inputs:{extended:[2,"extended","extended",y]},exportAs:["matButton","matAnchor"],features:[Y],attrs:ir,ngContentSelectors:Pi,decls:7,vars:4,consts:[[1,"mat-mdc-button-persistent-ripple"],[1,"mdc-button__label"],[1,"mat-focus-indicator"],[1,"mat-mdc-button-touch-target"]],template:function(e,o){e&1&&($(Ti),X(0,"span",0),N(1),Ct(2,"span",1),N(3,1),St(),N(4,2),X(5,"span",2)(6,"span",3)),e&2&&L("mdc-button__ripple",!o._isFab)("mdc-fab__ripple",o._isFab)},styles:[`.mat-mdc-fab-base {
  -webkit-user-select: none;
  user-select: none;
  position: relative;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  box-sizing: border-box;
  width: 56px;
  height: 56px;
  padding: 0;
  border: none;
  fill: currentColor;
  text-decoration: none;
  cursor: pointer;
  -moz-appearance: none;
  -webkit-appearance: none;
  overflow: visible;
  transition: box-shadow 280ms cubic-bezier(0.4, 0, 0.2, 1), opacity 15ms linear 30ms, transform 270ms 0ms cubic-bezier(0, 0, 0.2, 1);
  flex-shrink: 0;
  -webkit-tap-highlight-color: transparent;
}
.mat-mdc-fab-base .mat-mdc-button-ripple,
.mat-mdc-fab-base .mat-mdc-button-persistent-ripple,
.mat-mdc-fab-base .mat-mdc-button-persistent-ripple::before {
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  position: absolute;
  pointer-events: none;
  border-radius: inherit;
}
.mat-mdc-fab-base .mat-mdc-button-ripple {
  overflow: hidden;
}
.mat-mdc-fab-base .mat-mdc-button-persistent-ripple::before {
  content: "";
  opacity: 0;
}
.mat-mdc-fab-base .mdc-button__label,
.mat-mdc-fab-base .mat-icon {
  z-index: 1;
  position: relative;
}
.mat-mdc-fab-base .mat-focus-indicator {
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  position: absolute;
}
.mat-mdc-fab-base:focus-visible > .mat-focus-indicator::before {
  content: "";
}
.mat-mdc-fab-base._mat-animation-noopable {
  transition: none !important;
  animation: none !important;
}
.mat-mdc-fab-base::before {
  position: absolute;
  box-sizing: border-box;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  border: 1px solid transparent;
  border-radius: inherit;
  content: "";
  pointer-events: none;
}
.mat-mdc-fab-base[hidden] {
  display: none;
}
.mat-mdc-fab-base::-moz-focus-inner {
  padding: 0;
  border: 0;
}
.mat-mdc-fab-base:active, .mat-mdc-fab-base:focus {
  outline: none;
}
.mat-mdc-fab-base:hover {
  cursor: pointer;
}
.mat-mdc-fab-base > svg {
  width: 100%;
}
.mat-mdc-fab-base .mat-icon, .mat-mdc-fab-base .material-icons {
  transition: transform 180ms 90ms cubic-bezier(0, 0, 0.2, 1);
  fill: currentColor;
  will-change: transform;
}
.mat-mdc-fab-base .mat-focus-indicator::before {
  margin: calc(calc(var(--mat-focus-indicator-border-width, 3px) + 2px) * -1);
}
.mat-mdc-fab-base[disabled], .mat-mdc-fab-base.mat-mdc-button-disabled {
  cursor: default;
  pointer-events: none;
}
.mat-mdc-fab-base[disabled], .mat-mdc-fab-base[disabled]:focus, .mat-mdc-fab-base.mat-mdc-button-disabled, .mat-mdc-fab-base.mat-mdc-button-disabled:focus {
  box-shadow: none;
}
.mat-mdc-fab-base.mat-mdc-button-disabled-interactive {
  pointer-events: auto;
}

.mat-mdc-fab {
  background-color: var(--mat-fab-container-color, var(--mat-sys-primary-container));
  border-radius: var(--mat-fab-container-shape, var(--mat-sys-corner-large));
  color: var(--mat-fab-foreground-color, var(--mat-sys-on-primary-container, inherit));
  box-shadow: var(--mat-fab-container-elevation-shadow, var(--mat-sys-level3));
}
@media (hover: hover) {
  .mat-mdc-fab:hover {
    box-shadow: var(--mat-fab-hover-container-elevation-shadow, var(--mat-sys-level4));
  }
}
.mat-mdc-fab:focus {
  box-shadow: var(--mat-fab-focus-container-elevation-shadow, var(--mat-sys-level3));
}
.mat-mdc-fab:active, .mat-mdc-fab:focus:active {
  box-shadow: var(--mat-fab-pressed-container-elevation-shadow, var(--mat-sys-level3));
}
.mat-mdc-fab[disabled], .mat-mdc-fab.mat-mdc-button-disabled {
  cursor: default;
  pointer-events: none;
  color: var(--mat-fab-disabled-state-foreground-color, color-mix(in srgb, var(--mat-sys-on-surface) 38%, transparent));
  background-color: var(--mat-fab-disabled-state-container-color, color-mix(in srgb, var(--mat-sys-on-surface) 12%, transparent));
}
.mat-mdc-fab.mat-mdc-button-disabled-interactive {
  pointer-events: auto;
}
.mat-mdc-fab .mat-mdc-button-touch-target {
  position: absolute;
  top: 50%;
  height: var(--mat-fab-touch-target-size, 48px);
  display: var(--mat-fab-touch-target-display, block);
  left: 50%;
  width: var(--mat-fab-touch-target-size, 48px);
  transform: translate(-50%, -50%);
}
.mat-mdc-fab .mat-ripple-element {
  background-color: var(--mat-fab-ripple-color, color-mix(in srgb, var(--mat-sys-on-primary-container) calc(var(--mat-sys-pressed-state-layer-opacity) * 100%), transparent));
}
.mat-mdc-fab .mat-mdc-button-persistent-ripple::before {
  background-color: var(--mat-fab-state-layer-color, var(--mat-sys-on-primary-container));
}
.mat-mdc-fab.mat-mdc-button-disabled .mat-mdc-button-persistent-ripple::before {
  background-color: var(--mat-fab-disabled-state-layer-color);
}
.mat-mdc-fab:hover > .mat-mdc-button-persistent-ripple::before {
  opacity: var(--mat-fab-hover-state-layer-opacity, var(--mat-sys-hover-state-layer-opacity));
}
.mat-mdc-fab.cdk-program-focused > .mat-mdc-button-persistent-ripple::before, .mat-mdc-fab.cdk-keyboard-focused > .mat-mdc-button-persistent-ripple::before, .mat-mdc-fab.mat-mdc-button-disabled-interactive:focus > .mat-mdc-button-persistent-ripple::before {
  opacity: var(--mat-fab-focus-state-layer-opacity, var(--mat-sys-focus-state-layer-opacity));
}
.mat-mdc-fab:active > .mat-mdc-button-persistent-ripple::before {
  opacity: var(--mat-fab-pressed-state-layer-opacity, var(--mat-sys-pressed-state-layer-opacity));
}

.mat-mdc-mini-fab {
  width: 40px;
  height: 40px;
  background-color: var(--mat-fab-small-container-color, var(--mat-sys-primary-container));
  border-radius: var(--mat-fab-small-container-shape, var(--mat-sys-corner-medium));
  color: var(--mat-fab-small-foreground-color, var(--mat-sys-on-primary-container, inherit));
  box-shadow: var(--mat-fab-small-container-elevation-shadow, var(--mat-sys-level3));
}
@media (hover: hover) {
  .mat-mdc-mini-fab:hover {
    box-shadow: var(--mat-fab-small-hover-container-elevation-shadow, var(--mat-sys-level4));
  }
}
.mat-mdc-mini-fab:focus {
  box-shadow: var(--mat-fab-small-focus-container-elevation-shadow, var(--mat-sys-level3));
}
.mat-mdc-mini-fab:active, .mat-mdc-mini-fab:focus:active {
  box-shadow: var(--mat-fab-small-pressed-container-elevation-shadow, var(--mat-sys-level3));
}
.mat-mdc-mini-fab[disabled], .mat-mdc-mini-fab.mat-mdc-button-disabled {
  cursor: default;
  pointer-events: none;
  color: var(--mat-fab-small-disabled-state-foreground-color, color-mix(in srgb, var(--mat-sys-on-surface) 38%, transparent));
  background-color: var(--mat-fab-small-disabled-state-container-color, color-mix(in srgb, var(--mat-sys-on-surface) 12%, transparent));
}
.mat-mdc-mini-fab.mat-mdc-button-disabled-interactive {
  pointer-events: auto;
}
.mat-mdc-mini-fab .mat-mdc-button-touch-target {
  position: absolute;
  top: 50%;
  height: var(--mat-fab-small-touch-target-size, 48px);
  display: var(--mat-fab-small-touch-target-display);
  left: 50%;
  width: var(--mat-fab-small-touch-target-size, 48px);
  transform: translate(-50%, -50%);
}
.mat-mdc-mini-fab .mat-ripple-element {
  background-color: var(--mat-fab-small-ripple-color, color-mix(in srgb, var(--mat-sys-on-primary-container) calc(var(--mat-sys-pressed-state-layer-opacity) * 100%), transparent));
}
.mat-mdc-mini-fab .mat-mdc-button-persistent-ripple::before {
  background-color: var(--mat-fab-small-state-layer-color, var(--mat-sys-on-primary-container));
}
.mat-mdc-mini-fab.mat-mdc-button-disabled .mat-mdc-button-persistent-ripple::before {
  background-color: var(--mat-fab-small-disabled-state-layer-color);
}
.mat-mdc-mini-fab:hover > .mat-mdc-button-persistent-ripple::before {
  opacity: var(--mat-fab-small-hover-state-layer-opacity, var(--mat-sys-hover-state-layer-opacity));
}
.mat-mdc-mini-fab.cdk-program-focused > .mat-mdc-button-persistent-ripple::before, .mat-mdc-mini-fab.cdk-keyboard-focused > .mat-mdc-button-persistent-ripple::before, .mat-mdc-mini-fab.mat-mdc-button-disabled-interactive:focus > .mat-mdc-button-persistent-ripple::before {
  opacity: var(--mat-fab-small-focus-state-layer-opacity, var(--mat-sys-focus-state-layer-opacity));
}
.mat-mdc-mini-fab:active > .mat-mdc-button-persistent-ripple::before {
  opacity: var(--mat-fab-small-pressed-state-layer-opacity, var(--mat-sys-pressed-state-layer-opacity));
}

.mat-mdc-extended-fab {
  -moz-osx-font-smoothing: grayscale;
  -webkit-font-smoothing: antialiased;
  padding-left: 20px;
  padding-right: 20px;
  width: auto;
  max-width: 100%;
  line-height: normal;
  box-shadow: var(--mat-fab-extended-container-elevation-shadow, var(--mat-sys-level3));
  height: var(--mat-fab-extended-container-height, 56px);
  border-radius: var(--mat-fab-extended-container-shape, var(--mat-sys-corner-large));
  font-family: var(--mat-fab-extended-label-text-font, var(--mat-sys-label-large-font));
  font-size: var(--mat-fab-extended-label-text-size, var(--mat-sys-label-large-size));
  font-weight: var(--mat-fab-extended-label-text-weight, var(--mat-sys-label-large-weight));
  letter-spacing: var(--mat-fab-extended-label-text-tracking, var(--mat-sys-label-large-tracking));
}
@media (hover: hover) {
  .mat-mdc-extended-fab:hover {
    box-shadow: var(--mat-fab-extended-hover-container-elevation-shadow, var(--mat-sys-level4));
  }
}
.mat-mdc-extended-fab:focus {
  box-shadow: var(--mat-fab-extended-focus-container-elevation-shadow, var(--mat-sys-level3));
}
.mat-mdc-extended-fab:active, .mat-mdc-extended-fab:focus:active {
  box-shadow: var(--mat-fab-extended-pressed-container-elevation-shadow, var(--mat-sys-level3));
}
.mat-mdc-extended-fab[disabled], .mat-mdc-extended-fab.mat-mdc-button-disabled {
  cursor: default;
  pointer-events: none;
}
.mat-mdc-extended-fab[disabled], .mat-mdc-extended-fab[disabled]:focus, .mat-mdc-extended-fab.mat-mdc-button-disabled, .mat-mdc-extended-fab.mat-mdc-button-disabled:focus {
  box-shadow: none;
}
.mat-mdc-extended-fab.mat-mdc-button-disabled-interactive {
  pointer-events: auto;
}
[dir=rtl] .mat-mdc-extended-fab .mdc-button__label + .mat-icon, [dir=rtl] .mat-mdc-extended-fab .mdc-button__label + .material-icons,
.mat-mdc-extended-fab > .mat-icon,
.mat-mdc-extended-fab > .material-icons {
  margin-left: -8px;
  margin-right: 12px;
}
.mat-mdc-extended-fab .mdc-button__label + .mat-icon,
.mat-mdc-extended-fab .mdc-button__label + .material-icons, [dir=rtl] .mat-mdc-extended-fab > .mat-icon, [dir=rtl] .mat-mdc-extended-fab > .material-icons {
  margin-left: 12px;
  margin-right: -8px;
}
.mat-mdc-extended-fab .mat-mdc-button-touch-target {
  width: 100%;
}
`],encapsulation:2,changeDetection:0})}return i})();var bc=(()=>{class i{static \u0275fac=function(e){return new(e||i)};static \u0275mod=I({type:i});static \u0275inj=C({imports:[Ai,U]})}return i})();var Nt=class{_attachedHost=null;attach(n){return this._attachedHost=n,n.attach(this)}detach(){let n=this._attachedHost;n!=null&&(this._attachedHost=null,n.detach())}get isAttached(){return this._attachedHost!=null}setAttachedHost(n){this._attachedHost=n}},Je=class extends Nt{component;viewContainerRef;injector;projectableNodes;bindings;constructor(n,t,e,o,r){super(),this.component=n,this.viewContainerRef=t,this.injector=e,this.projectableNodes=o,this.bindings=r||null}},Lt=class extends Nt{templateRef;viewContainerRef;context;injector;constructor(n,t,e,o){super(),this.templateRef=n,this.viewContainerRef=t,this.context=e,this.injector=o}get origin(){return this.templateRef.elementRef}attach(n,t=this.context){return this.context=t,super.attach(n)}detach(){return this.context=void 0,super.detach()}},tn=class extends Nt{element;constructor(n){super(),this.element=n instanceof v?n.nativeElement:n}},be=class{_attachedPortal=null;_disposeFn=null;_isDisposed=!1;hasAttached(){return!!this._attachedPortal}attach(n){if(n instanceof Je)return this._attachedPortal=n,this.attachComponentPortal(n);if(n instanceof Lt)return this._attachedPortal=n,this.attachTemplatePortal(n);if(this.attachDomPortal&&n instanceof tn)return this._attachedPortal=n,this.attachDomPortal(n)}attachDomPortal=null;detach(){this._attachedPortal&&(this._attachedPortal.setAttachedHost(null),this._attachedPortal=null),this._invokeDisposeFn()}dispose(){this.hasAttached()&&this.detach(),this._invokeDisposeFn(),this._isDisposed=!0}setDisposeFn(n){this._disposeFn=n}_invokeDisposeFn(){this._disposeFn&&(this._disposeFn(),this._disposeFn=null)}},_e=class extends be{outletElement;_appRef;_defaultInjector;constructor(n,t,e){super(),this.outletElement=n,this._appRef=t,this._defaultInjector=e}attachComponentPortal(n){let t;if(n.viewContainerRef){let e=n.injector||n.viewContainerRef.injector,o=e.get(Re,null,{optional:!0})||void 0;t=n.viewContainerRef.createComponent(n.component,{index:n.viewContainerRef.length,injector:e,ngModuleRef:o,projectableNodes:n.projectableNodes||void 0,bindings:n.bindings||void 0}),this.setDisposeFn(()=>t.destroy())}else{let e=this._appRef,o=n.injector||this._defaultInjector||S.NULL,r=o.get(dt,e.injector);t=Qt(n.component,{elementInjector:o,environmentInjector:r,projectableNodes:n.projectableNodes||void 0,bindings:n.bindings||void 0}),e.attachView(t.hostView),this.setDisposeFn(()=>{e.viewCount>0&&e.detachView(t.hostView),t.destroy()})}return this.outletElement.appendChild(this._getComponentRootNode(t)),this._attachedPortal=n,t}attachTemplatePortal(n){let t=n.viewContainerRef,e=t.createEmbeddedView(n.templateRef,n.context,{injector:n.injector});return e.rootNodes.forEach(o=>this.outletElement.appendChild(o)),e.detectChanges(),this.setDisposeFn(()=>{let o=t.indexOf(e);o!==-1&&t.remove(o)}),this._attachedPortal=n,e}attachDomPortal=n=>{let t=n.element;t.parentNode;let e=this.outletElement.ownerDocument.createComment("dom-portal");t.parentNode.insertBefore(e,t),this.outletElement.appendChild(t),this._attachedPortal=n,super.setDisposeFn(()=>{e.parentNode&&e.parentNode.replaceChild(t,e)})};dispose(){super.dispose(),this.outletElement.remove()}_getComponentRootNode(n){return n.hostView.rootNodes[0]}};var xc=(()=>{class i extends be{_moduleRef=a(Re,{optional:!0});_document=a(b);_viewContainerRef=a(Et);_isInitialized=!1;_attachedRef=null;constructor(){super()}get portal(){return this._attachedPortal}set portal(t){this.hasAttached()&&!t&&!this._isInitialized||(this.hasAttached()&&super.detach(),t&&super.attach(t),this._attachedPortal=t||null)}attached=new F;get attachedRef(){return this._attachedRef}ngOnInit(){this._isInitialized=!0}ngOnDestroy(){super.dispose(),this._attachedRef=this._attachedPortal=null}attachComponentPortal(t){t.setAttachedHost(this);let e=t.viewContainerRef!=null?t.viewContainerRef:this._viewContainerRef,o=e.createComponent(t.component,{index:e.length,injector:t.injector||e.injector,projectableNodes:t.projectableNodes||void 0,ngModuleRef:this._moduleRef||void 0,bindings:t.bindings||void 0});return e!==this._viewContainerRef&&this._getRootNode().appendChild(o.hostView.rootNodes[0]),super.setDisposeFn(()=>o.destroy()),this._attachedPortal=t,this._attachedRef=o,this.attached.emit(o),o}attachTemplatePortal(t){t.setAttachedHost(this);let e=this._viewContainerRef.createEmbeddedView(t.templateRef,t.context,{injector:t.injector});return super.setDisposeFn(()=>this._viewContainerRef.clear()),this._attachedPortal=t,this._attachedRef=e,this.attached.emit(e),e}attachDomPortal=t=>{let e=t.element;e.parentNode;let o=this._document.createComment("dom-portal");t.setAttachedHost(this),e.parentNode.insertBefore(o,e),this._getRootNode().appendChild(e),this._attachedPortal=t,super.setDisposeFn(()=>{o.parentNode&&o.parentNode.replaceChild(e,o)})};_getRootNode(){let t=this._viewContainerRef.element.nativeElement;return t.nodeType===t.ELEMENT_NODE?t:t.parentNode}static \u0275fac=function(e){return new(e||i)};static \u0275dir=k({type:i,selectors:[["","cdkPortalOutlet",""]],inputs:{portal:[0,"cdkPortalOutlet","portal"]},outputs:{attached:"attached"},exportAs:["cdkPortalOutlet"],features:[Y]})}return i})(),Fi=(()=>{class i{static \u0275fac=function(e){return new(e||i)};static \u0275mod=I({type:i});static \u0275inj=C({})}return i})();var Ni=ee();function Ui(i){return new ge(i.get(ot),i.get(b))}var ge=class{_viewportRuler;_previousHTMLStyles={top:"",left:""};_previousScrollPosition;_isEnabled=!1;_document;constructor(n,t){this._viewportRuler=n,this._document=t}attach(){}enable(){if(this._canBeEnabled()){let n=this._document.documentElement;this._previousScrollPosition=this._viewportRuler.getViewportScrollPosition(),this._previousHTMLStyles.left=n.style.left||"",this._previousHTMLStyles.top=n.style.top||"",n.style.left=E(-this._previousScrollPosition.left),n.style.top=E(-this._previousScrollPosition.top),n.classList.add("cdk-global-scrollblock"),this._isEnabled=!0}}disable(){if(this._isEnabled){let n=this._document.documentElement,t=this._document.body,e=n.style,o=t.style,r=e.scrollBehavior||"",s=o.scrollBehavior||"";this._isEnabled=!1,e.left=this._previousHTMLStyles.left,e.top=this._previousHTMLStyles.top,n.classList.remove("cdk-global-scrollblock"),Ni&&(e.scrollBehavior=o.scrollBehavior="auto"),window.scroll(this._previousScrollPosition.left,this._previousScrollPosition.top),Ni&&(e.scrollBehavior=r,o.scrollBehavior=s)}}_canBeEnabled(){if(this._document.documentElement.classList.contains("cdk-global-scrollblock")||this._isEnabled)return!1;let t=this._document.documentElement,e=this._viewportRuler.getViewportSize();return t.scrollHeight>e.height||t.scrollWidth>e.width}};function Wi(i,n){return new ve(i.get(Rt),i.get(_),i.get(ot),n)}var ve=class{_scrollDispatcher;_ngZone;_viewportRuler;_config;_scrollSubscription=null;_overlayRef;_initialScrollPosition;constructor(n,t,e,o){this._scrollDispatcher=n,this._ngZone=t,this._viewportRuler=e,this._config=o}attach(n){this._overlayRef,this._overlayRef=n}enable(){if(this._scrollSubscription)return;let n=this._scrollDispatcher.scrolled(0).pipe(tt(t=>!t||!this._overlayRef.overlayElement.contains(t.getElementRef().nativeElement)));this._config&&this._config.threshold&&this._config.threshold>1?(this._initialScrollPosition=this._viewportRuler.getViewportScrollPosition().top,this._scrollSubscription=n.subscribe(()=>{let t=this._viewportRuler.getViewportScrollPosition().top;Math.abs(t-this._initialScrollPosition)>this._config.threshold?this._detach():this._overlayRef.updatePosition()})):this._scrollSubscription=n.subscribe(this._detach)}disable(){this._scrollSubscription&&(this._scrollSubscription.unsubscribe(),this._scrollSubscription=null)}detach(){this.disable(),this._overlayRef=null}_detach=()=>{this.disable(),this._overlayRef.hasAttached()&&this._ngZone.run(()=>this._overlayRef.detach())}};var Bt=class{enable(){}disable(){}attach(){}};function en(i,n){return n.some(t=>{let e=i.bottom<t.top,o=i.top>t.bottom,r=i.right<t.left,s=i.left>t.right;return e||o||r||s})}function Li(i,n){return n.some(t=>{let e=i.top<t.top,o=i.bottom>t.bottom,r=i.left<t.left,s=i.right>t.right;return e||o||r||s})}function rn(i,n){return new ye(i.get(Rt),i.get(ot),i.get(_),n)}var ye=class{_scrollDispatcher;_viewportRuler;_ngZone;_config;_scrollSubscription=null;_overlayRef;constructor(n,t,e,o){this._scrollDispatcher=n,this._viewportRuler=t,this._ngZone=e,this._config=o}attach(n){this._overlayRef,this._overlayRef=n}enable(){if(!this._scrollSubscription){let n=this._config?this._config.scrollThrottle:0;this._scrollSubscription=this._scrollDispatcher.scrolled(n).subscribe(()=>{if(this._overlayRef.updatePosition(),this._config&&this._config.autoClose){let t=this._overlayRef.overlayElement.getBoundingClientRect(),{width:e,height:o}=this._viewportRuler.getViewportSize();en(t,[{width:e,height:o,bottom:o,right:e,top:0,left:0}])&&(this.disable(),this._ngZone.run(()=>this._overlayRef.detach()))}})}}disable(){this._scrollSubscription&&(this._scrollSubscription.unsubscribe(),this._scrollSubscription=null)}detach(){this.disable(),this._overlayRef=null}},Yi=(()=>{class i{_injector=a(S);constructor(){}noop=()=>new Bt;close=t=>Wi(this._injector,t);block=()=>Ui(this._injector);reposition=t=>rn(this._injector,t);static \u0275fac=function(e){return new(e||i)};static \u0275prov=h({token:i,factory:i.\u0275fac,providedIn:"root"})}return i})(),zt=class{positionStrategy;scrollStrategy=new Bt;panelClass="";hasBackdrop=!1;backdropClass="cdk-overlay-dark-backdrop";disableAnimations;width;height;minWidth;minHeight;maxWidth;maxHeight;direction;disposeOnNavigation=!1;usePopover;eventPredicate;constructor(n){if(n){let t=Object.keys(n);for(let e of t)n[e]!==void 0&&(this[e]=n[e])}}};var we=class{connectionPair;scrollableViewProperties;constructor(n,t){this.connectionPair=n,this.scrollableViewProperties=t}};var Xi=(()=>{class i{_attachedOverlays=[];_document=a(b);_isAttached=!1;constructor(){}ngOnDestroy(){this.detach()}add(t){this.remove(t),this._attachedOverlays.push(t)}remove(t){let e=this._attachedOverlays.indexOf(t);e>-1&&this._attachedOverlays.splice(e,1),this._attachedOverlays.length===0&&this.detach()}canReceiveEvent(t,e,o){return o.observers.length<1?!1:t.eventPredicate?t.eventPredicate(e):!0}static \u0275fac=function(e){return new(e||i)};static \u0275prov=h({token:i,factory:i.\u0275fac,providedIn:"root"})}return i})(),Ki=(()=>{class i extends Xi{_ngZone=a(_);_renderer=a(j).createRenderer(null,null);_cleanupKeydown;add(t){super.add(t),this._isAttached||(this._ngZone.runOutsideAngular(()=>{this._cleanupKeydown=this._renderer.listen("body","keydown",this._keydownListener)}),this._isAttached=!0)}detach(){this._isAttached&&(this._cleanupKeydown?.(),this._isAttached=!1)}_keydownListener=t=>{let e=this._attachedOverlays;for(let o=e.length-1;o>-1;o--){let r=e[o];if(this.canReceiveEvent(r,t,r._keydownEvents)){this._ngZone.run(()=>r._keydownEvents.next(t));break}}};static \u0275fac=(()=>{let t;return function(o){return(t||(t=Ie(i)))(o||i)}})();static \u0275prov=h({token:i,factory:i.\u0275fac,providedIn:"root"})}return i})(),Zi=(()=>{class i extends Xi{_platform=a(g);_ngZone=a(_);_renderer=a(j).createRenderer(null,null);_cursorOriginalValue;_cursorStyleIsSet=!1;_pointerDownEventTarget=null;_cleanups;add(t){if(super.add(t),!this._isAttached){let e=this._document.body,o={capture:!0},r=this._renderer;this._cleanups=this._ngZone.runOutsideAngular(()=>[r.listen(e,"pointerdown",this._pointerDownListener,o),r.listen(e,"click",this._clickListener,o),r.listen(e,"auxclick",this._clickListener,o),r.listen(e,"contextmenu",this._clickListener,o)]),this._platform.IOS&&!this._cursorStyleIsSet&&(this._cursorOriginalValue=e.style.cursor,e.style.cursor="pointer",this._cursorStyleIsSet=!0),this._isAttached=!0}}detach(){this._isAttached&&(this._cleanups?.forEach(t=>t()),this._cleanups=void 0,this._platform.IOS&&this._cursorStyleIsSet&&(this._document.body.style.cursor=this._cursorOriginalValue,this._cursorStyleIsSet=!1),this._isAttached=!1)}_pointerDownListener=t=>{this._pointerDownEventTarget=T(t)};_clickListener=t=>{let e=T(t),o=t.type==="click"&&this._pointerDownEventTarget?this._pointerDownEventTarget:e;this._pointerDownEventTarget=null;let r=this._attachedOverlays.slice();for(let s=r.length-1;s>-1;s--){let l=r[s],c=l._outsidePointerEvents;if(!(!l.hasAttached()||!this.canReceiveEvent(l,t,c))){if(Bi(l.overlayElement,e)||Bi(l.overlayElement,o))break;this._ngZone?this._ngZone.run(()=>c.next(t)):c.next(t)}}};static \u0275fac=(()=>{let t;return function(o){return(t||(t=Ie(i)))(o||i)}})();static \u0275prov=h({token:i,factory:i.\u0275fac,providedIn:"root"})}return i})();function Bi(i,n){let t=typeof ShadowRoot<"u"&&ShadowRoot,e=n;for(;e;){if(e===i)return!0;e=t&&e instanceof ShadowRoot?e.host:e.parentNode}return!1}var $i=(()=>{class i{static \u0275fac=function(e){return new(e||i)};static \u0275cmp=O({type:i,selectors:[["ng-component"]],hostAttrs:["cdk-overlay-style-loader",""],decls:0,vars:0,template:function(e,o){},styles:[`.cdk-overlay-container, .cdk-global-overlay-wrapper {
  pointer-events: none;
  top: 0;
  left: 0;
  height: 100%;
  width: 100%;
}

.cdk-overlay-container {
  position: fixed;
}
@layer cdk-overlay {
  .cdk-overlay-container {
    z-index: 1000;
  }
}
.cdk-overlay-container:empty {
  display: none;
}

.cdk-global-overlay-wrapper {
  display: flex;
  position: absolute;
}
@layer cdk-overlay {
  .cdk-global-overlay-wrapper {
    z-index: 1000;
  }
}

.cdk-overlay-pane {
  position: absolute;
  pointer-events: auto;
  box-sizing: border-box;
  display: flex;
  max-width: 100%;
  max-height: 100%;
}
@layer cdk-overlay {
  .cdk-overlay-pane {
    z-index: 1000;
  }
}

.cdk-overlay-backdrop {
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  pointer-events: auto;
  -webkit-tap-highlight-color: transparent;
  opacity: 0;
  touch-action: manipulation;
}
@layer cdk-overlay {
  .cdk-overlay-backdrop {
    z-index: 1000;
    transition: opacity 400ms cubic-bezier(0.25, 0.8, 0.25, 1);
  }
}
@media (prefers-reduced-motion) {
  .cdk-overlay-backdrop {
    transition-duration: 1ms;
  }
}

.cdk-overlay-backdrop-showing {
  opacity: 1;
}
@media (forced-colors: active) {
  .cdk-overlay-backdrop-showing {
    opacity: 0.6;
  }
}

@layer cdk-overlay {
  .cdk-overlay-dark-backdrop {
    background: rgba(0, 0, 0, 0.32);
  }
}

.cdk-overlay-transparent-backdrop {
  transition: visibility 1ms linear, opacity 1ms linear;
  visibility: hidden;
  opacity: 1;
}
.cdk-overlay-transparent-backdrop.cdk-overlay-backdrop-showing, .cdk-high-contrast-active .cdk-overlay-transparent-backdrop {
  opacity: 0;
  visibility: visible;
}

.cdk-overlay-backdrop-noop-animation {
  transition: none;
}

.cdk-overlay-connected-position-bounding-box {
  position: absolute;
  display: flex;
  flex-direction: column;
  min-width: 1px;
  min-height: 1px;
}
@layer cdk-overlay {
  .cdk-overlay-connected-position-bounding-box {
    z-index: 1000;
  }
}

.cdk-global-scrollblock {
  position: fixed;
  width: 100%;
  overflow-y: scroll;
}

.cdk-overlay-popover {
  background: none;
  border: none;
  padding: 0;
  outline: 0;
  overflow: visible;
  position: fixed;
  pointer-events: none;
  white-space: normal;
  color: inherit;
  text-decoration: none;
  width: 100%;
  height: 100%;
  inset: auto;
  top: 0;
  left: 0;
}
.cdk-overlay-popover::backdrop {
  display: none;
}
.cdk-overlay-popover .cdk-overlay-backdrop {
  position: fixed;
  z-index: auto;
}
`],encapsulation:2,changeDetection:0})}return i})(),Gi=(()=>{class i{_platform=a(g);_containerElement;_document=a(b);_styleLoader=a(B);constructor(){}ngOnDestroy(){this._containerElement?.remove()}getContainerElement(){return this._loadStyles(),this._containerElement||this._createContainer(),this._containerElement}_createContainer(){let t="cdk-overlay-container";if(this._platform.isBrowser||Xe()){let o=this._document.querySelectorAll(`.${t}[platform="server"], .${t}[platform="test"]`);for(let r=0;r<o.length;r++)o[r].remove()}let e=this._document.createElement("div");e.classList.add(t),Xe()?e.setAttribute("platform","test"):this._platform.isBrowser||e.setAttribute("platform","server"),this._document.body.appendChild(e),this._containerElement=e}_loadStyles(){this._styleLoader.load($i)}static \u0275fac=function(e){return new(e||i)};static \u0275prov=h({token:i,factory:i.\u0275fac,providedIn:"root"})}return i})(),nn=class{_renderer;_ngZone;element;_cleanupClick;_cleanupTransitionEnd;_fallbackTimeout;constructor(n,t,e,o){this._renderer=t,this._ngZone=e,this.element=n.createElement("div"),this.element.classList.add("cdk-overlay-backdrop"),this._cleanupClick=t.listen(this.element,"click",o)}detach(){this._ngZone.runOutsideAngular(()=>{let n=this.element;clearTimeout(this._fallbackTimeout),this._cleanupTransitionEnd?.(),this._cleanupTransitionEnd=this._renderer.listen(n,"transitionend",this.dispose),this._fallbackTimeout=setTimeout(this.dispose,500),n.style.pointerEvents="none",n.classList.remove("cdk-overlay-backdrop-showing")})}dispose=()=>{clearTimeout(this._fallbackTimeout),this._cleanupClick?.(),this._cleanupTransitionEnd?.(),this._cleanupClick=this._cleanupTransitionEnd=this._fallbackTimeout=void 0,this.element.remove()}};function sn(i){return i&&i.nodeType===1}var Ee=class{_portalOutlet;_host;_pane;_config;_ngZone;_keyboardDispatcher;_document;_location;_outsideClickDispatcher;_animationsDisabled;_injector;_renderer;_backdropClick=new f;_attachments=new f;_detachments=new f;_positionStrategy;_scrollStrategy;_locationChanges=P.EMPTY;_backdropRef=null;_detachContentMutationObserver;_detachContentAfterRenderRef;_disposed=!1;_previousHostParent;_keydownEvents=new f;_outsidePointerEvents=new f;_afterNextRenderRef;constructor(n,t,e,o,r,s,l,c,u,d=!1,m,A){this._portalOutlet=n,this._host=t,this._pane=e,this._config=o,this._ngZone=r,this._keyboardDispatcher=s,this._document=l,this._location=c,this._outsideClickDispatcher=u,this._animationsDisabled=d,this._injector=m,this._renderer=A,o.scrollStrategy&&(this._scrollStrategy=o.scrollStrategy,this._scrollStrategy.attach(this)),this._positionStrategy=o.positionStrategy}get overlayElement(){return this._pane}get backdropElement(){return this._backdropRef?.element||null}get hostElement(){return this._host}get eventPredicate(){return this._config?.eventPredicate||null}attach(n){if(this._disposed)return null;this._attachHost();let t=this._portalOutlet.attach(n);return this._positionStrategy?.attach(this),this._updateStackingOrder(),this._updateElementSize(),this._updateElementDirection(),this._scrollStrategy&&this._scrollStrategy.enable(),this._afterNextRenderRef?.destroy(),this._afterNextRenderRef=nt(()=>{this.hasAttached()&&this.updatePosition()},{injector:this._injector}),this._togglePointerEvents(!0),this._config.hasBackdrop&&this._attachBackdrop(),this._config.panelClass&&this._toggleClasses(this._pane,this._config.panelClass,!0),this._attachments.next(),this._completeDetachContent(),this._keyboardDispatcher.add(this),this._config.disposeOnNavigation&&(this._locationChanges=this._location.subscribe(()=>this.dispose())),this._outsideClickDispatcher.add(this),typeof t?.onDestroy=="function"&&t.onDestroy(()=>{this.hasAttached()&&this._ngZone.runOutsideAngular(()=>Promise.resolve().then(()=>this.detach()))}),t}detach(){if(!this.hasAttached())return;this.detachBackdrop(),this._togglePointerEvents(!1),this._positionStrategy&&this._positionStrategy.detach&&this._positionStrategy.detach(),this._scrollStrategy&&this._scrollStrategy.disable();let n=this._portalOutlet.detach();return this._detachments.next(),this._completeDetachContent(),this._keyboardDispatcher.remove(this),this._detachContentWhenEmpty(),this._locationChanges.unsubscribe(),this._outsideClickDispatcher.remove(this),n}dispose(){if(this._disposed)return;let n=this.hasAttached();this._positionStrategy&&this._positionStrategy.dispose(),this._disposeScrollStrategy(),this._backdropRef?.dispose(),this._locationChanges.unsubscribe(),this._keyboardDispatcher.remove(this),this._portalOutlet.dispose(),this._attachments.complete(),this._backdropClick.complete(),this._keydownEvents.complete(),this._outsidePointerEvents.complete(),this._outsideClickDispatcher.remove(this),this._host?.remove(),this._afterNextRenderRef?.destroy(),this._previousHostParent=this._pane=this._host=this._backdropRef=null,n&&this._detachments.next(),this._detachments.complete(),this._completeDetachContent(),this._disposed=!0}hasAttached(){return this._portalOutlet.hasAttached()}backdropClick(){return this._backdropClick}attachments(){return this._attachments}detachments(){return this._detachments}keydownEvents(){return this._keydownEvents}outsidePointerEvents(){return this._outsidePointerEvents}getConfig(){return this._config}updatePosition(){this._positionStrategy&&this._positionStrategy.apply()}updatePositionStrategy(n){n!==this._positionStrategy&&(this._positionStrategy&&this._positionStrategy.dispose(),this._positionStrategy=n,this.hasAttached()&&(n.attach(this),this.updatePosition()))}updateSize(n){this._config=x(x({},this._config),n),this._updateElementSize()}setDirection(n){this._config=hn(x({},this._config),{direction:n}),this._updateElementDirection()}addPanelClass(n){this._pane&&this._toggleClasses(this._pane,n,!0)}removePanelClass(n){this._pane&&this._toggleClasses(this._pane,n,!1)}getDirection(){let n=this._config.direction;return n?typeof n=="string"?n:n.value:"ltr"}updateScrollStrategy(n){n!==this._scrollStrategy&&(this._disposeScrollStrategy(),this._scrollStrategy=n,this.hasAttached()&&(n.attach(this),n.enable()))}_updateElementDirection(){this._host.setAttribute("dir",this.getDirection())}_updateElementSize(){if(!this._pane)return;let n=this._pane.style;n.width=E(this._config.width),n.height=E(this._config.height),n.minWidth=E(this._config.minWidth),n.minHeight=E(this._config.minHeight),n.maxWidth=E(this._config.maxWidth),n.maxHeight=E(this._config.maxHeight)}_togglePointerEvents(n){this._pane.style.pointerEvents=n?"":"none"}_attachHost(){if(!this._host.parentElement){let n=this._config.usePopover?this._positionStrategy?.getPopoverInsertionPoint?.():null;sn(n)?n.after(this._host):n?.type==="parent"?n.element.appendChild(this._host):this._previousHostParent?.appendChild(this._host)}if(this._config.usePopover)try{this._host.showPopover()}catch(n){}}_attachBackdrop(){let n="cdk-overlay-backdrop-showing";this._backdropRef?.dispose(),this._backdropRef=new nn(this._document,this._renderer,this._ngZone,t=>{this._backdropClick.next(t)}),this._animationsDisabled&&this._backdropRef.element.classList.add("cdk-overlay-backdrop-noop-animation"),this._config.backdropClass&&this._toggleClasses(this._backdropRef.element,this._config.backdropClass,!0),this._config.usePopover?this._host.prepend(this._backdropRef.element):this._host.parentElement.insertBefore(this._backdropRef.element,this._host),!this._animationsDisabled&&typeof requestAnimationFrame<"u"?this._ngZone.runOutsideAngular(()=>{requestAnimationFrame(()=>this._backdropRef?.element.classList.add(n))}):this._backdropRef.element.classList.add(n)}_updateStackingOrder(){!this._config.usePopover&&this._host.nextSibling&&this._host.parentNode.appendChild(this._host)}detachBackdrop(){this._animationsDisabled?(this._backdropRef?.dispose(),this._backdropRef=null):this._backdropRef?.detach()}_toggleClasses(n,t,e){let o=st(t||[]).filter(r=>!!r);o.length&&(e?n.classList.add(...o):n.classList.remove(...o))}_detachContentWhenEmpty(){let n=!1;try{this._detachContentAfterRenderRef=nt(()=>{n=!0,this._detachContent()},{injector:this._injector})}catch(t){if(n)throw t;this._detachContent()}globalThis.MutationObserver&&this._pane&&(this._detachContentMutationObserver||=new globalThis.MutationObserver(()=>{this._detachContent()}),this._detachContentMutationObserver.observe(this._pane,{childList:!0}))}_detachContent(){(!this._pane||!this._host||this._pane.children.length===0)&&(this._pane&&this._config.panelClass&&this._toggleClasses(this._pane,this._config.panelClass,!1),this._host&&this._host.parentElement&&(this._previousHostParent=this._host.parentElement,this._host.remove()),this._completeDetachContent())}_completeDetachContent(){this._detachContentAfterRenderRef?.destroy(),this._detachContentAfterRenderRef=void 0,this._detachContentMutationObserver?.disconnect()}_disposeScrollStrategy(){let n=this._scrollStrategy;n?.disable(),n?.detach?.()}},zi="cdk-overlay-connected-position-bounding-box",sr=/([A-Za-z%]+)$/;function an(i,n){return new xe(n,i.get(ot),i.get(b),i.get(g),i.get(Gi))}var xe=class{_viewportRuler;_document;_platform;_overlayContainer;_overlayRef;_isInitialRender=!1;_lastBoundingBoxSize={width:0,height:0};_isPushed=!1;_canPush=!0;_growAfterOpen=!1;_hasFlexibleDimensions=!0;_positionLocked=!1;_originRect;_overlayRect;_viewportRect;_containerRect;_viewportMargin=0;_scrollables=[];_preferredPositions=[];_origin;_pane;_isDisposed=!1;_boundingBox=null;_lastPosition=null;_lastScrollVisibility=null;_positionChanges=new f;_resizeSubscription=P.EMPTY;_offsetX=0;_offsetY=0;_transformOriginSelector;_appliedPanelClasses=[];_previousPushAmount=null;_popoverLocation="global";positionChanges=this._positionChanges;get positions(){return this._preferredPositions}constructor(n,t,e,o,r){this._viewportRuler=t,this._document=e,this._platform=o,this._overlayContainer=r,this.setOrigin(n)}attach(n){this._overlayRef&&this._overlayRef,this._validatePositions(),n.hostElement.classList.add(zi),this._overlayRef=n,this._boundingBox=n.hostElement,this._pane=n.overlayElement,this._isDisposed=!1,this._isInitialRender=!0,this._lastPosition=null,this._resizeSubscription.unsubscribe(),this._resizeSubscription=this._viewportRuler.change().subscribe(()=>{this._isInitialRender=!0,this.apply()})}apply(){if(this._isDisposed||!this._platform.isBrowser)return;if(!this._isInitialRender&&this._positionLocked&&this._lastPosition){this.reapplyLastPosition();return}this._clearPanelClasses(),this._resetOverlayElementStyles(),this._resetBoundingBoxStyles(),this._viewportRect=this._getNarrowedViewportRect(),this._originRect=this._getOriginRect(),this._overlayRect=this._pane.getBoundingClientRect(),this._containerRect=this._getContainerRect();let n=this._originRect,t=this._overlayRect,e=this._viewportRect,o=this._containerRect,r=[],s;for(let l of this._preferredPositions){let c=this._getOriginPoint(n,o,l),u=this._getOverlayPoint(c,t,l),d=this._getOverlayFit(u,t,e,l);if(d.isCompletelyWithinViewport){this._isPushed=!1,this._applyPosition(l,c);return}if(this._canFitWithFlexibleDimensions(d,u,e)){r.push({position:l,origin:c,overlayRect:t,boundingBoxRect:this._calculateBoundingBoxRect(c,l)});continue}(!s||s.overlayFit.visibleArea<d.visibleArea)&&(s={overlayFit:d,overlayPoint:u,originPoint:c,position:l,overlayRect:t})}if(r.length){let l=null,c=-1;for(let u of r){let d=u.boundingBoxRect.width*u.boundingBoxRect.height*(u.position.weight||1);d>c&&(c=d,l=u)}this._isPushed=!1,this._applyPosition(l.position,l.origin);return}if(this._canPush){this._isPushed=!0,this._applyPosition(s.position,s.originPoint);return}this._applyPosition(s.position,s.originPoint)}detach(){this._clearPanelClasses(),this._lastPosition=null,this._previousPushAmount=null,this._resizeSubscription.unsubscribe()}dispose(){this._isDisposed||(this._boundingBox&&lt(this._boundingBox.style,{top:"",left:"",right:"",bottom:"",height:"",width:"",alignItems:"",justifyContent:""}),this._pane&&this._resetOverlayElementStyles(),this._overlayRef&&this._overlayRef.hostElement.classList.remove(zi),this.detach(),this._positionChanges.complete(),this._overlayRef=this._boundingBox=null,this._isDisposed=!0)}reapplyLastPosition(){if(this._isDisposed||!this._platform.isBrowser)return;let n=this._lastPosition;n?(this._originRect=this._getOriginRect(),this._overlayRect=this._pane.getBoundingClientRect(),this._viewportRect=this._getNarrowedViewportRect(),this._containerRect=this._getContainerRect(),this._applyPosition(n,this._getOriginPoint(this._originRect,this._containerRect,n))):this.apply()}withScrollableContainers(n){return this._scrollables=n,this}withPositions(n){return this._preferredPositions=n,n.indexOf(this._lastPosition)===-1&&(this._lastPosition=null),this._validatePositions(),this}withViewportMargin(n){return this._viewportMargin=n,this}withFlexibleDimensions(n=!0){return this._hasFlexibleDimensions=n,this}withGrowAfterOpen(n=!0){return this._growAfterOpen=n,this}withPush(n=!0){return this._canPush=n,this}withLockedPosition(n=!0){return this._positionLocked=n,this}setOrigin(n){return this._origin=n,this}withDefaultOffsetX(n){return this._offsetX=n,this}withDefaultOffsetY(n){return this._offsetY=n,this}withTransformOriginOn(n){return this._transformOriginSelector=n,this}withPopoverLocation(n){return this._popoverLocation=n,this}getPopoverInsertionPoint(){return this._popoverLocation==="global"?null:this._popoverLocation!=="inline"?this._popoverLocation:this._origin instanceof v?this._origin.nativeElement:sn(this._origin)?this._origin:null}_getOriginPoint(n,t,e){let o;if(e.originX=="center")o=n.left+n.width/2;else{let s=this._isRtl()?n.right:n.left,l=this._isRtl()?n.left:n.right;o=e.originX=="start"?s:l}t.left<0&&(o-=t.left);let r;return e.originY=="center"?r=n.top+n.height/2:r=e.originY=="top"?n.top:n.bottom,t.top<0&&(r-=t.top),{x:o,y:r}}_getOverlayPoint(n,t,e){let o;e.overlayX=="center"?o=-t.width/2:e.overlayX==="start"?o=this._isRtl()?-t.width:0:o=this._isRtl()?0:-t.width;let r;return e.overlayY=="center"?r=-t.height/2:r=e.overlayY=="top"?0:-t.height,{x:n.x+o,y:n.y+r}}_getOverlayFit(n,t,e,o){let r=ji(t),{x:s,y:l}=n,c=this._getOffset(o,"x"),u=this._getOffset(o,"y");c&&(s+=c),u&&(l+=u);let d=0-s,m=s+r.width-e.width,A=0-l,M=l+r.height-e.height,D=this._subtractOverflows(r.width,d,m),R=this._subtractOverflows(r.height,A,M),J=D*R;return{visibleArea:J,isCompletelyWithinViewport:r.width*r.height===J,fitsInViewportVertically:R===r.height,fitsInViewportHorizontally:D==r.width}}_canFitWithFlexibleDimensions(n,t,e){if(this._hasFlexibleDimensions){let o=e.bottom-t.y,r=e.right-t.x,s=Vi(this._overlayRef.getConfig().minHeight),l=Vi(this._overlayRef.getConfig().minWidth),c=n.fitsInViewportVertically||s!=null&&s<=o,u=n.fitsInViewportHorizontally||l!=null&&l<=r;return c&&u}return!1}_pushOverlayOnScreen(n,t,e){if(this._previousPushAmount&&this._positionLocked)return{x:n.x+this._previousPushAmount.x,y:n.y+this._previousPushAmount.y};let o=ji(t),r=this._viewportRect,s=Math.max(n.x+o.width-r.width,0),l=Math.max(n.y+o.height-r.height,0),c=Math.max(r.top-e.top-n.y,0),u=Math.max(r.left-e.left-n.x,0),d=0,m=0;return o.width<=r.width?d=u||-s:d=n.x<this._getViewportMarginStart()?r.left-e.left-n.x:0,o.height<=r.height?m=c||-l:m=n.y<this._getViewportMarginTop()?r.top-e.top-n.y:0,this._previousPushAmount={x:d,y:m},{x:n.x+d,y:n.y+m}}_applyPosition(n,t){if(this._setTransformOrigin(n),this._setOverlayElementStyles(t,n),this._setBoundingBoxStyles(t,n),n.panelClass&&this._addPanelClasses(n.panelClass),this._positionChanges.observers.length){let e=this._getScrollVisibility();if(n!==this._lastPosition||!this._lastScrollVisibility||!ar(this._lastScrollVisibility,e)){let o=new we(n,e);this._positionChanges.next(o)}this._lastScrollVisibility=e}this._lastPosition=n,this._isInitialRender=!1}_setTransformOrigin(n){if(!this._transformOriginSelector)return;let t=this._boundingBox.querySelectorAll(this._transformOriginSelector),e,o=n.overlayY;n.overlayX==="center"?e="center":this._isRtl()?e=n.overlayX==="start"?"right":"left":e=n.overlayX==="start"?"left":"right";for(let r=0;r<t.length;r++)t[r].style.transformOrigin=`${e} ${o}`}_calculateBoundingBoxRect(n,t){let e=this._viewportRect,o=this._isRtl(),r,s,l;if(t.overlayY==="top")s=n.y,r=e.height-s+this._getViewportMarginBottom();else if(t.overlayY==="bottom")l=e.height-n.y+this._getViewportMarginTop()+this._getViewportMarginBottom(),r=e.height-l+this._getViewportMarginTop();else{let M=Math.min(e.bottom-n.y+e.top,n.y),D=this._lastBoundingBoxSize.height;r=M*2,s=n.y-M,r>D&&!this._isInitialRender&&!this._growAfterOpen&&(s=n.y-D/2)}let c=t.overlayX==="start"&&!o||t.overlayX==="end"&&o,u=t.overlayX==="end"&&!o||t.overlayX==="start"&&o,d,m,A;if(u)A=e.width-n.x+this._getViewportMarginStart()+this._getViewportMarginEnd(),d=n.x-this._getViewportMarginStart();else if(c)m=n.x,d=e.right-n.x-this._getViewportMarginEnd();else{let M=Math.min(e.right-n.x+e.left,n.x),D=this._lastBoundingBoxSize.width;d=M*2,m=n.x-M,d>D&&!this._isInitialRender&&!this._growAfterOpen&&(m=n.x-D/2)}return{top:s,left:m,bottom:l,right:A,width:d,height:r}}_setBoundingBoxStyles(n,t){let e=this._calculateBoundingBoxRect(n,t);!this._isInitialRender&&!this._growAfterOpen&&(e.height=Math.min(e.height,this._lastBoundingBoxSize.height),e.width=Math.min(e.width,this._lastBoundingBoxSize.width));let o={};if(this._hasExactPosition())o.top=o.left="0",o.bottom=o.right="auto",o.maxHeight=o.maxWidth="",o.width=o.height="100%";else{let r=this._overlayRef.getConfig().maxHeight,s=this._overlayRef.getConfig().maxWidth;o.width=E(e.width),o.height=E(e.height),o.top=E(e.top)||"auto",o.bottom=E(e.bottom)||"auto",o.left=E(e.left)||"auto",o.right=E(e.right)||"auto",t.overlayX==="center"?o.alignItems="center":o.alignItems=t.overlayX==="end"?"flex-end":"flex-start",t.overlayY==="center"?o.justifyContent="center":o.justifyContent=t.overlayY==="bottom"?"flex-end":"flex-start",r&&(o.maxHeight=E(r)),s&&(o.maxWidth=E(s))}this._lastBoundingBoxSize=e,lt(this._boundingBox.style,o)}_resetBoundingBoxStyles(){lt(this._boundingBox.style,{top:"0",left:"0",right:"0",bottom:"0",height:"",width:"",alignItems:"",justifyContent:""})}_resetOverlayElementStyles(){lt(this._pane.style,{top:"",left:"",bottom:"",right:"",position:"",transform:""})}_setOverlayElementStyles(n,t){let e={},o=this._hasExactPosition(),r=this._hasFlexibleDimensions,s=this._overlayRef.getConfig();if(o){let d=this._viewportRuler.getViewportScrollPosition();lt(e,this._getExactOverlayY(t,n,d)),lt(e,this._getExactOverlayX(t,n,d))}else e.position="static";let l="",c=this._getOffset(t,"x"),u=this._getOffset(t,"y");c&&(l+=`translateX(${c}px) `),u&&(l+=`translateY(${u}px)`),e.transform=l.trim(),s.maxHeight&&(o?e.maxHeight=E(s.maxHeight):r&&(e.maxHeight="")),s.maxWidth&&(o?e.maxWidth=E(s.maxWidth):r&&(e.maxWidth="")),lt(this._pane.style,e)}_getExactOverlayY(n,t,e){let o={top:"",bottom:""},r=this._getOverlayPoint(t,this._overlayRect,n);if(this._isPushed&&(r=this._pushOverlayOnScreen(r,this._overlayRect,e)),n.overlayY==="bottom"){let s=this._document.documentElement.clientHeight;o.bottom=`${s-(r.y+this._overlayRect.height)}px`}else o.top=E(r.y);return o}_getExactOverlayX(n,t,e){let o={left:"",right:""},r=this._getOverlayPoint(t,this._overlayRect,n);this._isPushed&&(r=this._pushOverlayOnScreen(r,this._overlayRect,e));let s;if(this._isRtl()?s=n.overlayX==="end"?"left":"right":s=n.overlayX==="end"?"right":"left",s==="right"){let l=this._document.documentElement.clientWidth;o.right=`${l-(r.x+this._overlayRect.width)}px`}else o.left=E(r.x);return o}_getScrollVisibility(){let n=this._getOriginRect(),t=this._pane.getBoundingClientRect(),e=this._scrollables.map(o=>o.getElementRef().nativeElement.getBoundingClientRect());return{isOriginClipped:Li(n,e),isOriginOutsideView:en(n,e),isOverlayClipped:Li(t,e),isOverlayOutsideView:en(t,e)}}_subtractOverflows(n,...t){return t.reduce((e,o)=>e-Math.max(o,0),n)}_getNarrowedViewportRect(){let n=this._document.documentElement.clientWidth,t=this._document.documentElement.clientHeight,e=this._viewportRuler.getViewportScrollPosition();return{top:e.top+this._getViewportMarginTop(),left:e.left+this._getViewportMarginStart(),right:e.left+n-this._getViewportMarginEnd(),bottom:e.top+t-this._getViewportMarginBottom(),width:n-this._getViewportMarginStart()-this._getViewportMarginEnd(),height:t-this._getViewportMarginTop()-this._getViewportMarginBottom()}}_isRtl(){return this._overlayRef.getDirection()==="rtl"}_hasExactPosition(){return!this._hasFlexibleDimensions||this._isPushed}_getOffset(n,t){return t==="x"?n.offsetX==null?this._offsetX:n.offsetX:n.offsetY==null?this._offsetY:n.offsetY}_validatePositions(){}_addPanelClasses(n){this._pane&&st(n).forEach(t=>{t!==""&&this._appliedPanelClasses.indexOf(t)===-1&&(this._appliedPanelClasses.push(t),this._pane.classList.add(t))})}_clearPanelClasses(){this._pane&&(this._appliedPanelClasses.forEach(n=>{this._pane.classList.remove(n)}),this._appliedPanelClasses=[])}_getViewportMarginStart(){return typeof this._viewportMargin=="number"?this._viewportMargin:this._viewportMargin?.start??0}_getViewportMarginEnd(){return typeof this._viewportMargin=="number"?this._viewportMargin:this._viewportMargin?.end??0}_getViewportMarginTop(){return typeof this._viewportMargin=="number"?this._viewportMargin:this._viewportMargin?.top??0}_getViewportMarginBottom(){return typeof this._viewportMargin=="number"?this._viewportMargin:this._viewportMargin?.bottom??0}_getOriginRect(){let n=this._origin;if(n instanceof v)return n.nativeElement.getBoundingClientRect();if(n instanceof Element)return n.getBoundingClientRect();let t=n.width||0,e=n.height||0;return{top:n.y,bottom:n.y+e,left:n.x,right:n.x+t,height:e,width:t}}_getContainerRect(){let n=this._overlayRef.getConfig().usePopover&&this._popoverLocation!=="global",t=this._overlayContainer.getContainerElement();n&&(t.style.display="block");let e=t.getBoundingClientRect();return n&&(t.style.display=""),e}};function lt(i,n){for(let t in n)n.hasOwnProperty(t)&&(i[t]=n[t]);return i}function Vi(i){if(typeof i!="number"&&i!=null){let[n,t]=i.split(sr);return!t||t==="px"?parseFloat(n):null}return i||null}function ji(i){return{top:Math.floor(i.top),right:Math.floor(i.right),bottom:Math.floor(i.bottom),left:Math.floor(i.left),width:Math.floor(i.width),height:Math.floor(i.height)}}function ar(i,n){return i===n?!0:i.isOriginClipped===n.isOriginClipped&&i.isOriginOutsideView===n.isOriginOutsideView&&i.isOverlayClipped===n.isOverlayClipped&&i.isOverlayOutsideView===n.isOverlayOutsideView}var Hi="cdk-global-overlay-wrapper";function qi(i){return new Ce}var Ce=class{_overlayRef;_cssPosition="static";_topOffset="";_bottomOffset="";_alignItems="";_xPosition="";_xOffset="";_width="";_height="";_isDisposed=!1;attach(n){let t=n.getConfig();this._overlayRef=n,this._width&&!t.width&&n.updateSize({width:this._width}),this._height&&!t.height&&n.updateSize({height:this._height}),n.hostElement.classList.add(Hi),this._isDisposed=!1}top(n=""){return this._bottomOffset="",this._topOffset=n,this._alignItems="flex-start",this}left(n=""){return this._xOffset=n,this._xPosition="left",this}bottom(n=""){return this._topOffset="",this._bottomOffset=n,this._alignItems="flex-end",this}right(n=""){return this._xOffset=n,this._xPosition="right",this}start(n=""){return this._xOffset=n,this._xPosition="start",this}end(n=""){return this._xOffset=n,this._xPosition="end",this}width(n=""){return this._overlayRef?this._overlayRef.updateSize({width:n}):this._width=n,this}height(n=""){return this._overlayRef?this._overlayRef.updateSize({height:n}):this._height=n,this}centerHorizontally(n=""){return this.left(n),this._xPosition="center",this}centerVertically(n=""){return this.top(n),this._alignItems="center",this}apply(){if(!this._overlayRef||!this._overlayRef.hasAttached())return;let n=this._overlayRef.overlayElement.style,t=this._overlayRef.hostElement.style,e=this._overlayRef.getConfig(),{width:o,height:r,maxWidth:s,maxHeight:l}=e,c=(o==="100%"||o==="100vw")&&(!s||s==="100%"||s==="100vw"),u=(r==="100%"||r==="100vh")&&(!l||l==="100%"||l==="100vh"),d=this._xPosition,m=this._xOffset,A=this._overlayRef.getConfig().direction==="rtl",M="",D="",R="";c?R="flex-start":d==="center"?(R="center",A?D=m:M=m):A?d==="left"||d==="end"?(R="flex-end",M=m):(d==="right"||d==="start")&&(R="flex-start",D=m):d==="left"||d==="start"?(R="flex-start",M=m):(d==="right"||d==="end")&&(R="flex-end",D=m),n.position=this._cssPosition,n.marginLeft=c?"0":M,n.marginTop=u?"0":this._topOffset,n.marginBottom=this._bottomOffset,n.marginRight=c?"0":D,t.justifyContent=R,t.alignItems=u?"flex-start":this._alignItems}dispose(){if(this._isDisposed||!this._overlayRef)return;let n=this._overlayRef.overlayElement.style,t=this._overlayRef.hostElement,e=t.style;t.classList.remove(Hi),e.justifyContent=e.alignItems=n.marginTop=n.marginBottom=n.marginLeft=n.marginRight=n.position="",this._overlayRef=null,this._isDisposed=!0}},Qi=(()=>{class i{_injector=a(S);constructor(){}global(){return qi()}flexibleConnectedTo(t){return an(this._injector,t)}static \u0275fac=function(e){return new(e||i)};static \u0275prov=h({token:i,factory:i.\u0275fac,providedIn:"root"})}return i})(),ln=new p("OVERLAY_DEFAULT_CONFIG");function cn(i,n){i.get(B).load($i);let t=i.get(Gi),e=i.get(b),o=i.get(Tt),r=i.get(mt),s=i.get(pt),l=i.get(ht,null,{optional:!0})||i.get(j).createRenderer(null,null),c=new zt(n),u=i.get(ln,null,{optional:!0})?.usePopover??!0;c.direction=c.direction||s.value,"showPopover"in e.body?c.usePopover=n?.usePopover??u:c.usePopover=!1;let d=e.createElement("div"),m=e.createElement("div");d.id=o.getId("cdk-overlay-"),d.classList.add("cdk-overlay-pane"),m.appendChild(d),c.usePopover&&(m.setAttribute("popover","manual"),m.classList.add("cdk-overlay-popover"));let A=c.usePopover?c.positionStrategy?.getPopoverInsertionPoint?.():null;return sn(A)?A.after(m):A?.type==="parent"?A.element.appendChild(m):t.getContainerElement().appendChild(m),new Ee(new _e(d,r,i),m,d,c,i.get(_),i.get(Ki),e,i.get(Bn),i.get(Zi),n?.disableAnimations??i.get(Zt,null,{optional:!0})==="NoopAnimations",i.get(dt),l)}var Ji=(()=>{class i{scrollStrategies=a(Yi);_positionBuilder=a(Qi);_injector=a(S);constructor(){}create(t){return cn(this._injector,t)}position(){return this._positionBuilder}static \u0275fac=function(e){return new(e||i)};static \u0275prov=h({token:i,factory:i.\u0275fac,providedIn:"root"})}return i})(),lr=[{originX:"start",originY:"bottom",overlayX:"start",overlayY:"top"},{originX:"start",originY:"top",overlayX:"start",overlayY:"bottom"},{originX:"end",originY:"top",overlayX:"end",overlayY:"bottom"},{originX:"end",originY:"bottom",overlayX:"end",overlayY:"top"}],cr=new p("cdk-connected-overlay-scroll-strategy",{providedIn:"root",factory:()=>{let i=a(S);return()=>rn(i)}}),on=(()=>{class i{elementRef=a(v);constructor(){}static \u0275fac=function(e){return new(e||i)};static \u0275dir=k({type:i,selectors:[["","cdk-overlay-origin",""],["","overlay-origin",""],["","cdkOverlayOrigin",""]],exportAs:["cdkOverlayOrigin"]})}return i})(),to=new p("cdk-connected-overlay-default-config"),dr=(()=>{class i{_dir=a(pt,{optional:!0});_injector=a(S);_overlayRef;_templatePortal;_backdropSubscription=P.EMPTY;_attachSubscription=P.EMPTY;_detachSubscription=P.EMPTY;_positionSubscription=P.EMPTY;_offsetX;_offsetY;_position;_scrollStrategyFactory=a(cr);_ngZone=a(_);origin;positions;positionStrategy;get offsetX(){return this._offsetX}set offsetX(t){this._offsetX=t,this._position&&this._updatePositionStrategy(this._position)}get offsetY(){return this._offsetY}set offsetY(t){this._offsetY=t,this._position&&this._updatePositionStrategy(this._position)}width;height;minWidth;minHeight;backdropClass;panelClass;viewportMargin=0;scrollStrategy;open=!1;disableClose=!1;transformOriginSelector;hasBackdrop=!1;lockPosition=!1;flexibleDimensions=!1;growAfterOpen=!1;push=!1;disposeOnNavigation=!1;usePopover;matchWidth=!1;set _config(t){typeof t!="string"&&this._assignConfig(t)}backdropClick=new F;positionChange=new F;attach=new F;detach=new F;overlayKeydown=new F;overlayOutsideClick=new F;constructor(){let t=a($t),e=a(Et),o=a(to,{optional:!0}),r=a(ln,{optional:!0});this.usePopover=r?.usePopover===!1?null:"global",this._templatePortal=new Lt(t,e),this.scrollStrategy=this._scrollStrategyFactory(),o&&this._assignConfig(o)}get overlayRef(){return this._overlayRef}get dir(){return this._dir?this._dir.value:"ltr"}ngOnDestroy(){this._attachSubscription.unsubscribe(),this._detachSubscription.unsubscribe(),this._backdropSubscription.unsubscribe(),this._positionSubscription.unsubscribe(),this._overlayRef?.dispose()}ngOnChanges(t){this._position&&(this._updatePositionStrategy(this._position),this._overlayRef?.updateSize({width:this._getWidth(),minWidth:this.minWidth,height:this.height,minHeight:this.minHeight}),t.origin&&this.open&&this._position.apply()),t.open&&(this.open?this.attachOverlay():this.detachOverlay())}_createOverlay(){(!this.positions||!this.positions.length)&&(this.positions=lr);let t=this._overlayRef=cn(this._injector,this._buildConfig());this._attachSubscription=t.attachments().subscribe(()=>this.attach.emit()),this._detachSubscription=t.detachments().subscribe(()=>this.detach.emit()),t.keydownEvents().subscribe(e=>{this.overlayKeydown.next(e),e.keyCode===27&&!this.disableClose&&!de(e)&&(e.preventDefault(),this.detachOverlay())}),this._overlayRef.outsidePointerEvents().subscribe(e=>{let o=this._getOriginElement(),r=T(e);(!o||o!==r&&!o.contains(r))&&this.overlayOutsideClick.next(e)})}_buildConfig(){let t=this._position=this.positionStrategy||this._createPositionStrategy(),e=new zt({direction:this._dir||"ltr",positionStrategy:t,scrollStrategy:this.scrollStrategy,hasBackdrop:this.hasBackdrop,disposeOnNavigation:this.disposeOnNavigation,usePopover:!!this.usePopover});return(this.height||this.height===0)&&(e.height=this.height),(this.minWidth||this.minWidth===0)&&(e.minWidth=this.minWidth),(this.minHeight||this.minHeight===0)&&(e.minHeight=this.minHeight),this.backdropClass&&(e.backdropClass=this.backdropClass),this.panelClass&&(e.panelClass=this.panelClass),e}_updatePositionStrategy(t){let e=this.positions.map(o=>({originX:o.originX,originY:o.originY,overlayX:o.overlayX,overlayY:o.overlayY,offsetX:o.offsetX||this.offsetX,offsetY:o.offsetY||this.offsetY,panelClass:o.panelClass||void 0}));return t.setOrigin(this._getOrigin()).withPositions(e).withFlexibleDimensions(this.flexibleDimensions).withPush(this.push).withGrowAfterOpen(this.growAfterOpen).withViewportMargin(this.viewportMargin).withLockedPosition(this.lockPosition).withTransformOriginOn(this.transformOriginSelector).withPopoverLocation(this.usePopover===null?"global":this.usePopover)}_createPositionStrategy(){let t=an(this._injector,this._getOrigin());return this._updatePositionStrategy(t),t}_getOrigin(){return this.origin instanceof on?this.origin.elementRef:this.origin}_getOriginElement(){return this.origin instanceof on?this.origin.elementRef.nativeElement:this.origin instanceof v?this.origin.nativeElement:typeof Element<"u"&&this.origin instanceof Element?this.origin:null}_getWidth(){return this.width?this.width:this.matchWidth?this._getOriginElement()?.getBoundingClientRect?.().width:void 0}attachOverlay(){this._overlayRef||this._createOverlay();let t=this._overlayRef;t.getConfig().hasBackdrop=this.hasBackdrop,t.updateSize({width:this._getWidth()}),t.hasAttached()||t.attach(this._templatePortal),this.hasBackdrop?this._backdropSubscription=t.backdropClick().subscribe(e=>this.backdropClick.emit(e)):this._backdropSubscription.unsubscribe(),this._positionSubscription.unsubscribe(),this.positionChange.observers.length>0&&(this._positionSubscription=this._position.positionChanges.pipe(Cn(()=>this.positionChange.observers.length>0)).subscribe(e=>{this._ngZone.run(()=>this.positionChange.emit(e)),this.positionChange.observers.length===0&&this._positionSubscription.unsubscribe()})),this.open=!0}detachOverlay(){this._overlayRef?.detach(),this._backdropSubscription.unsubscribe(),this._positionSubscription.unsubscribe(),this.open=!1}_assignConfig(t){this.origin=t.origin??this.origin,this.positions=t.positions??this.positions,this.positionStrategy=t.positionStrategy??this.positionStrategy,this.offsetX=t.offsetX??this.offsetX,this.offsetY=t.offsetY??this.offsetY,this.width=t.width??this.width,this.height=t.height??this.height,this.minWidth=t.minWidth??this.minWidth,this.minHeight=t.minHeight??this.minHeight,this.backdropClass=t.backdropClass??this.backdropClass,this.panelClass=t.panelClass??this.panelClass,this.viewportMargin=t.viewportMargin??this.viewportMargin,this.scrollStrategy=t.scrollStrategy??this.scrollStrategy,this.disableClose=t.disableClose??this.disableClose,this.transformOriginSelector=t.transformOriginSelector??this.transformOriginSelector,this.hasBackdrop=t.hasBackdrop??this.hasBackdrop,this.lockPosition=t.lockPosition??this.lockPosition,this.flexibleDimensions=t.flexibleDimensions??this.flexibleDimensions,this.growAfterOpen=t.growAfterOpen??this.growAfterOpen,this.push=t.push??this.push,this.disposeOnNavigation=t.disposeOnNavigation??this.disposeOnNavigation,this.usePopover=t.usePopover??this.usePopover,this.matchWidth=t.matchWidth??this.matchWidth}static \u0275fac=function(e){return new(e||i)};static \u0275dir=k({type:i,selectors:[["","cdk-connected-overlay",""],["","connected-overlay",""],["","cdkConnectedOverlay",""]],inputs:{origin:[0,"cdkConnectedOverlayOrigin","origin"],positions:[0,"cdkConnectedOverlayPositions","positions"],positionStrategy:[0,"cdkConnectedOverlayPositionStrategy","positionStrategy"],offsetX:[0,"cdkConnectedOverlayOffsetX","offsetX"],offsetY:[0,"cdkConnectedOverlayOffsetY","offsetY"],width:[0,"cdkConnectedOverlayWidth","width"],height:[0,"cdkConnectedOverlayHeight","height"],minWidth:[0,"cdkConnectedOverlayMinWidth","minWidth"],minHeight:[0,"cdkConnectedOverlayMinHeight","minHeight"],backdropClass:[0,"cdkConnectedOverlayBackdropClass","backdropClass"],panelClass:[0,"cdkConnectedOverlayPanelClass","panelClass"],viewportMargin:[0,"cdkConnectedOverlayViewportMargin","viewportMargin"],scrollStrategy:[0,"cdkConnectedOverlayScrollStrategy","scrollStrategy"],open:[0,"cdkConnectedOverlayOpen","open"],disableClose:[0,"cdkConnectedOverlayDisableClose","disableClose"],transformOriginSelector:[0,"cdkConnectedOverlayTransformOriginOn","transformOriginSelector"],hasBackdrop:[2,"cdkConnectedOverlayHasBackdrop","hasBackdrop",y],lockPosition:[2,"cdkConnectedOverlayLockPosition","lockPosition",y],flexibleDimensions:[2,"cdkConnectedOverlayFlexibleDimensions","flexibleDimensions",y],growAfterOpen:[2,"cdkConnectedOverlayGrowAfterOpen","growAfterOpen",y],push:[2,"cdkConnectedOverlayPush","push",y],disposeOnNavigation:[2,"cdkConnectedOverlayDisposeOnNavigation","disposeOnNavigation",y],usePopover:[0,"cdkConnectedOverlayUsePopover","usePopover"],matchWidth:[2,"cdkConnectedOverlayMatchWidth","matchWidth",y],_config:[0,"cdkConnectedOverlay","_config"]},outputs:{backdropClick:"backdropClick",positionChange:"positionChange",attach:"attach",detach:"detach",overlayKeydown:"overlayKeydown",overlayOutsideClick:"overlayOutsideClick"},exportAs:["cdkConnectedOverlay"],features:[ut]})}return i})(),ur=(()=>{class i{static \u0275fac=function(e){return new(e||i)};static \u0275mod=I({type:i});static \u0275inj=C({providers:[Ji],imports:[U,Fi,Pe,Pe]})}return i})();export{g as a,Ne as b,Le as c,T as d,B as e,st as f,It as g,jn as h,K as i,pt as j,Un as k,xr as l,kt as m,Wn as n,U as o,so as p,Rt as q,Te as r,ot as s,mo as t,po as u,Me as v,Pe as w,Tt as x,Je as y,Lt as z,be as A,_e as B,xc as C,Fi as D,de as E,Ui as F,rn as G,zt as H,Gi as I,Ee as J,an as K,xe as L,qi as M,ln as N,cn as O,on as P,dr as Q,ur as R,Dt as S,Ot as T,ae as U,xo as V,oe as W,le as X,ze as Y,ci as Z,ko as _,pi as $,Bo as aa,Vo as ba,jo as ca,vl as da,yl as ea,hl as fa,gt as ga,He as ha,Uo as ia,Wo as ja,Ja as ka,al as la,Is as ma,ks as na,Fl as oa,Di as pa,er as qa,Ai as ra,pc as sa,fc as ta,bc as ua};
