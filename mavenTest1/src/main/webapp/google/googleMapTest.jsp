<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>구글맵 추가하기</title>
<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?key=AIzaSyCPylRXerp6Mzm3dh7vP16silCpPTXccnA"></script>
<script type="text/javascript" src="/js/jquery-1.12.0.js"></script>
<script type="text/javascript">
	var map;
	var myPosition = {
		latitude:37.478710, 
	    longitude:126.878700
	};
	function getMyLocation(){
	//브라우저가 지오로케이션 API를 지원하는지 여부를 검사하는 부분
	//navigator.geolocation객체가 존재하면 지원하는 것이다.
		if(navigator.geolocation){
	//지원하고 있다면 getCurrentPosition메소드를 호출해서 displayLocation이란 핸들러 함수를 호출함
	//navigator.geolocation속성은 지오로케이션 API전체를 포함하는 객체임.
	//이 API의 메인 메소드가 브라우저의 위치를 가져오는 일을 하는 getCurrentPosition임.
			navigator.geolocation.getCurrentPosition(displayLocation);
		}
		else{
			console.log("이런 지오로케이션이 제공되지 않아요.....");
		}
	}
	function displayLocation(position){
		console.log('displayLocation호출 성공');
		var latitude = position.coords.latitude;
		var longitude = position.coords.longitude;
		$("#location").text("당신의 위도 : "+latitude+", 당신의 경도 : "+longitude);
		showMap(position.coords);
	}
	function showMap(coords){
		foodInfo();//화면호출시 나오도록 테스트
		/*
			jsonResult : [object Object],[object Object],[object Object]
			jsonDoc : [object Object],[object Object],[object Object]
		*/
		var latNlong = new google.maps.LatLng(coords.latitude, coords.longitude);
		//alert("latNlong : "+latNlong);
		var mapOptions={
			zoom:16,
			center:latNlong,
			streetViewControl:true,//로드뷰
			mapTypeControl:false,//지도 타입
			fullscreenControl:false,
			//SATELITE와 HYBRID
			mapTypeId:google.maps.MapTypeId.ROADMAP
		};
		//자바스크립트에서는 DOM에 접근할 수 있다.
		//이 DOM에 접근할 때 배열표현식을 사용할 수 있음.
		//같은 이름이 여러개가 되면 자동으로 배열로 전환됨.
		// new javax.swing.JFrame  new java.util.HashMap
		map = new google.maps.Map($("#location")[0],mapOptions);
		//말풍선 추가하기
		var makerTitle = "박의가 추천하는 맛집";
		var parklatNlong = new google.maps.LatLng(37.480430, 126.879001);
		//마커객체 생성하기 - map객체 생성, 위치정보 담기
		var maker = new google.maps.Marker({
			position:parklatNlong//위치정보
		   ,map:map//맵정보
		   ,title:makerTitle//마커에 찍힐 제목정보
		});
		var makerMaxWidth = 300;//마커를 클릭했을 때 나타나는 말풍선의 최대 크기
		//말풍선의 내용
		var mcontent = "<div>";
		mcontent+="<h2>"+makerTitle+"</h2><p>맛있는 메뉴는 떡볶이와 돈까스예요 떡볶이는 밀떡을 써서 훨씬 쫄깃하고 라면과 쫄면은 불기전에 먹어야해요 그리고 3인이상이면 콜라 서비스..</p>";
		mcontent+="</div>";
		var infoWin = new google.maps.InfoWindow({
			content:mcontent,
			maxWidth:makerMaxWidth
		});
		//이벤트 처리
		google.maps.event.addListener(maker,'click',function(){
			infoWin.open(map,maker);
		});
		//맵을 누르면 말풍선 닫기
		google.maps.event.addListener(map,'click',function(){
			infoWin.close();
		});
		console.log("map:"+map);
	}
	function foodInfo(){
		$.ajax({//스프링방식으로 JSON받기 + RestController이용한 JSON바로 받기
			url:"../foodMap/resList"
		  , method:"get"
		  , dataType:"json"
		  , success:function(jsonResult){
			  alert("jsonResult : "+jsonResult);
			  var temp = JSON.stringify(jsonResult);
			  var jsonDoc = JSON.parse(temp);//배열로 변환
			  alert("jsonDoc : "+jsonDoc);
			  var html="<table board=:'1' boarderColor='orange'>";
			  for(var i=0;i<jsonDoc.length;i++){
				  alert(jsonDoc[i].rname+", "+jsonDoc[i],rtel);
				  html+="<tr>";
				  html+="<td>"+jsonDoc[i].rname+"</td>";
				  html+="<td>"+jsonDoc[i].rtel+"</td>";
				  html+="</tr>";
			  }
			  html+="</table>";
			  $("#d_foodInfo")
		  }
		  , error:function(xhrObject){
			  alert(xhrObject.responseText);
		  }
		});
	}
</script>
</head>
<body onLoad="getMyLocation()">
<input type="text" id="address" width="300px" value="월드메르디앙2차">
<!-- 검색 버튼 추가하기 -->
<input type="button" id="btn_search" value="검색" onClick="foodInfo()">
<div id="location" style="width:900px;height:700px;">
<!-- 여러분의 위치를 여기에 나타내 봐요... -->
</div>
<div id="d_foodInfo"></div>
</body>
</html>



