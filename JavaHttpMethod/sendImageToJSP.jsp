<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<link rel="shortcut icon" href="data:image/x-icon;," type="image/x-icon">
<link rel="stylesheet" href="/spring/resources/css/bootstrap.css">
<link rel="stylesheet" href="/spring/resources/css/main.css">
<script src="/spring/resources/js/jquery-1.12.4.js"></script>
<script src="/spring/resources/js/bootstrap.js"></script>
<script type="text/javascript" src="./resources/js/jquery-1.12.4.js"></script>

<style type="text/css">
	#tbl {
		width : 1570px;
		height : 450px;	
	}
	
</style>
<title>Test Page</title>
</head>
<body>
	
	<div class="container">
		<div id="header">
			<h1 class="page-header">Test Page</h1>
		</div>
		<table id="tbl" class="table table-bordered">
			<tbody>
				<tr>
					<th><h3>Result</h3></th>
				</tr>
				<tr>
					<td id="result" style="height:400px;"></td>			
				</tr>
			</tbody>
		</table>
		
		<br>
	
	<%-- <canvas id="canvas"></canvas> --%>
	<img src="#" id="blah" style="display: inline;" >
	<br><br>
	<form method="POST" enctype="multipart/form-data" id="fileUploadForm">
		<input type="file" id="file" name="imgFile" style="float:left; width:200px;"  name="file">
		<br><br>
		<input type="button" id="btnSubmit" class="btn btn-success" value="업로드">
		<div style="margin-top:15px">
			<img src="" id="output">
		</div>
	</form>
			
	<br><br>
	</div>
</body>

<script type="text/javascript">

	$(document).ready(function(){
		
$("#blah").hide();
		
		function dataURItoBlob(dataURI) {
			var byteString = atob(dataURI.split(',')[1]);
			var mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0]
			var ab = new ArrayBuffer(byteString.length);
			var ia = new Uint8Array(ab);
			for (var i = 0; i < byteString.length; i++) {
				ia[i] = byteString.charCodeAt(i);
			}

			var bb = new Blob([ab], { "type": mimeString });
			return bb;
		}
		
		function ResizeImage() {

			if($("#file").val() == ""){
				alert("이미지를 선택 해주세요");
			}

			var filesToUpload = document.getElementById('file').files;				 
			var file = filesToUpload[0];

			// 문서내에 img 객체를 생성합니다
			var img = document.createElement("img");
			// 파일을 읽을 수 있는 File Reader 를 생성
			var reader = new FileReader();

			// 파일이 읽혀지면 아래 함수가 실행			
			reader.onload = function(e) {
				img.src = e.target.result;

				// HTML5 canvas 객체를 생성
				var canvas = document.createElement("canvas");      
				var ctx = canvas.getContext("2d");

				// 캔버스에 업로드된 이미지를 그린다.
				ctx.drawImage(img, 0, 0);

				var MAX_WIDTH = 600;
				var MAX_HEIGHT = 300;
			
				canvas.width = MAX_WIDTH;
				canvas.height = MAX_HEIGHT;
						
				// canvas에 변경된 크기의 이미지를 다시 그려준다.
				var ctx = canvas.getContext("2d");				
				ctx.drawImage(img, 0, 0, canvas.width, canvas.height);
							
				//canvas 에 있는 이미지를 img 태그로 넣어줌.
				var dataurl = canvas.toDataURL("image/jpg");
				document.getElementById('blah').src = dataurl;
			}
			
			reader.readAsDataURL(file);

		}
		
		function readURL(input) {
			if (input.files && input.files[0]) {
				var reader = new FileReader(); //파일을 읽기 위한 FileReader객체 생성 
					reader.onload = function (e) { //파일 읽어들이기를 성공했을때 호출되는 이벤트 핸들러 
					$('#blah').attr('src', e.target.result); //이미지 Tag의 SRC속성에 읽어들인 File내용을 지정 
					//(아래 코드에서 읽어들인 dataURL형식) 
				} 
			
				reader.readAsDataURL(input.files[0]); 
				//File내용을 읽어 dataURL형식의 문자열로 저장 
			} 
		}
	
		//file 양식으로 이미지를 선택(값이 변경) 되었을때 처리하는 코드 
		$("#file").change(function(){ 
			
			//현재 등록된 이미지 hide처리.
			$("#canvas").hide();
			//변경된 이미지 show처리.
			$("#blah").show();

			//선택한 이미지 경로 표시 
			readURL(this); 
			/* ResizeImage(); */
		});
		
		//POST 방식 이미지 업로드 버튼
		$('#btnSubmit').off('click').on('click', function(){
			var num = 0;
			callAjax(num);
		});
		
		//버튼 클릭시 함수 발생 => ajax 통신 후 지연 반복 업로드 
		function callAjax(num) {
			
			var form = $("#fileUploadForm");
			
			var blob = dataURItoBlob(document.getElementById('blah').src);
			var data = new FormData(form[0]);	
			data.set("uploadedfile", blob);
				
			$.ajax({
				url: 'http://localhost:18080/spring/TestDo.action',
				type: 'POST',
				enctype: 'multipart/form-data',
				contentType: false,
				processData: false,
				cache: false,
				async:false,
				data: data,
				success: function(response){
					$('#result').html('Response : ' + response);
					
				},error: function(){
					console.log("error");
				}
			});
		}
		
	});
</script>

</html>
