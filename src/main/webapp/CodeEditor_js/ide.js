//for variable name box
var survey_options = document.getElementById('survey_options');
var variableValCounter = 0;
var variableValBox = " ";
var variableArray;
var variableName;
var variableVal;
var variableType;
var lineNo;

function showRandCountBox(this_boxElement){
	var randInputCountBox = document.getElementById("RandInputCountBox");
	if(this_boxElement.checked){
		randInputCountBox.removeAttribute("hidden");
	}
	else{
		randInputCountBox.setAttribute("hidden");		
	}
		
}

function addVariableBox(){
	variableArray = variableArray.trim();  //to remove unwanted white space
	variableArray = variableArray.substring(1,variableArray.length-1);
	variableArray = variableArray.trim();  //to remove unwanted white space
	if (variableArray.length != 0){
		variableArray = variableArray.split(",");
		console.log(variableArray);
		variableArray.sort();
		console.log(variableArray);
		var idx = 0; 
		var temp_=[];
		while(variableArray.length>idx){
			var temp = variableArray[idx].split("=");
			variableVal = temp[1];
			temp = temp[0].split("$");
			lineNo = Number(temp[0])+1;
			variableName = temp[1];
			variableType = temp[2];
			temp_.push([lineNo,variableName,variableType,variableVal]);
			idx+=1;
			}
		temp_.sort(function(a, b) {
			  return a[0] - b[0];
		});
		
		idx=0;
		while(temp_.length>idx){
			var newDiv = document.createElement('div');
			newDiv.setAttribute("class","variableVal");
			newDiv.setAttribute("id","box_"+variableValCounter++);
			var labelBox = '<label for="VariableValBox" class="varBoxLabel" id="varBoxLabel" > Line '+(temp_[idx][0]).toString()+" : "+temp_[idx][1]+' ( '+temp_[idx][2]+' ) </lable>'; 
			newDiv.innerHTML = labelBox;

			var input = document.createElement("input");
			if (temp_[idx][2]=="int"){
				input.setAttribute('type', 'text');
			}
			else if(temp_[idx][2]=="float"){
				input.setAttribute('type', 'text');
				//input.setAttribute('step', 'any');				
			}
			else{
				input.setAttribute('type', 'text');				
			}
			while (temp_[idx][3].indexOf("'%&%'")!=-1){
				temp_[idx][3]=temp_[idx][3].replace("'%&%'","','");
			}
			input.setAttribute('class','VariableValBox');
			input.setAttribute('id','VariableValBox');
			input.setAttribute('name','VariableValBox');
			input.setAttribute('placeholder',temp_[idx][3]);


			if (temp_[idx][2].indexOf("Array_")!=-1){
				input.setAttribute('required',"");
				input.required = true;
				var data = temp_[idx][3];
				while(data.indexOf("#@#")!=-1){
					data = data.replace("#@#",",");
				}
				input.setAttribute('value',data);
				input.setAttribute('placeholder',data);
			}

			newDiv.appendChild(input);	
			survey_options.appendChild(newDiv);	
			idx+=1;
			/*
			var newField = document.createElement('input'); 
			newField.setAttribute('type','text');
			newField.setAttribute('name','survey_options[]');
			newField.setAttribute('class','survey_options');
			newField.setAttribute('siz',50);
			newField.setAttribute('placeholder','Another Field');
			survey_options.appendChild(newField);
			*/
			}
		}
}

function removeVariableBox(){
	var input_tags = survey_options.getElementsByTagName('div');
	while(input_tags.length > 0) {
		survey_options.removeChild(input_tags[(input_tags.length) - 1]);
	}
}

function getVal_fromVarBox(){
  var varValEditor = document.getElementById("survey_options").getElementsByClassName("variableVal");
  var varArray = [];
  for(var i=0; i<varValEditor.length; i++){
	var temp = varValEditor[i].firstElementChild.textContent.split(" ");

	var lineNo = temp[2];
	var varName = temp[4];
	var varType = temp[6];
	var varValue = varValEditor[i].childNodes[1].value;
	
	while(varValue.indexOf("','")!=-1){
		varValue=varValue.replace("','","'%&%'")
	}
		
	//if array input
	if(varType.indexOf("Array_")!=-1){
		while(varValue.indexOf("{")!=-1){
			var start = varValue.indexOf("{");
			var end = varValue.indexOf("}");
			var data = varValue.substring(start,end+1);
			while(data.indexOf(",")!=-1){
				data=data.replace(",","#@#");
				}					
			data=data.replace("{","[");
			data=data.replace("}","]");
			varValue = varValue.substring(0,start)+data+varValue.substring(end+1);
			}
		while(varValue.indexOf("]")!=-1){
			varValue = varValue.replace("[","{");
			varValue = varValue.replace("]","}");
			}
		}
	//splitter "$#$"
	varArray.push([lineNo+"$#$"+varName+"$#$"+varType+"$#$"+varValue]);
	}
	return(varArray);	
}

let editor;
let editor_input;

window.onload = function() {
    editor = ace.edit("editor");
    editor.setTheme("ace/theme/monokai");
    editor.session.setMode("ace/mode/c_cpp");

	editor.setValue("Enter Your Code here");
	DisplayCode();

}

function cpp_fileUploder() {
  //Uploading file path
  var fileInput_Tag = document.getElementById("cpp_File_input1");
  var file = fileInput_Tag.files[0];
  var reader = new FileReader();
  console.log(fileInput_Tag.result);

  reader.onload = function(progressEvent){    
    var lines = fileInput_Tag.result.split("\n");

    for(var line = 0; line < lines.length; line++){

  	  editor.setValue(lines[line])
      console.log(line + " --> "+ lines[line]);
      //filling text (text, x,y)
    }
  };
	//alert("Its working");

  reader.readAsText(file);
 
}


function changeLanguage() {

    let language = $("#languages").val();

    if(language == 'c' || language == 'cpp')editor.session.setMode("ace/mode/c_cpp");
    else if(language == 'php')editor.session.setMode("ace/mode/php");
    else if(language == 'python')editor.session.setMode("ace/mode/python");
    else if(language == 'node')editor.session.setMode("ace/mode/javascript");
}


function getFileNameFromDrive(){

	$.ajax({
			url: 'checking', 
			type: 'POST', 
	
	        data: {
				Action : "getFileNameFromDrive",
	        },
	
	        success: function(result){
				file_name =result;
	            $(".output").html(result);
	        },
	        error: function(data) {
	            alert('woops display code!');
	        } 
	
		});
	
}


function DisplayFileOnDrive() {
	//alert("Display file from drive function");
	getFileNameFromDrive();
	
	console.log(file_name);
    var Options = "";

	var splitted_name = file_name.replace( /\n/g, "@" ).split( "@" ) ;
	file_name = "";
	var value =1;
    for ( temp in splitted_name) {
         Options += "<option value='"+value.toString()+"'>" + splitted_name[temp] + "</option>";
		 value+=1;
    }

    document.getElementById("File_display").innerHTML = Options;

		/*
     if (value.length == 0) document.getElementById("category").innerHTML = "<option></option>";
        else {
            var catOptions = "";
            for (categoryId in mealsByCategory[value]) {
                catOptions += "<option>" + mealsByCategory[value][categoryId] + "</option>";
            }
            document.getElementById("category").innerHTML = catOptions;
        }
    }*/
}

function GetSelectedItem(){
    var e = document.getElementById("File_display");
    var strSel = e.options[e.selectedIndex].text;

	$.ajax({
			url: 'checking', 
			type: 'POST', 
	
	        data: {
				Action : "GetSelectedItem",
				value  : strSel
	        },
	
	        success: function(result){
				file_name =result;
				//$(".output").html(result);
	            $(".output").html("Selected File "+strSel+" Retrived Successfully!!");
				DisplayCode();
	        },
	        error: function(data) {
	            alert('woops display code!');
	        } 
	
		});
	
}

function StoreCode() {
	var fileName = prompt("Enter Name of File: ");
	$.ajax({
			url: 'FileUpload', 
			type: 'GET', 
	
	        data: {
	            language: $("#languages").val(),
	            code: editor.getSession().getValue(),
				fileName: fileName
	        },
	
	        success: function(result){
	            //$(".output").html(result);
	            $(".output").html("Code Stored Successfully!");
	        },
	        error: function(data) {
	            alert('woops highlight code!');
	        } 
	
		});
 }

function HighlightCode(){
	removeVariableBox();
	$.ajax({
			url: 'HomePage', 
			type: 'GET', 
	
	        data: {
	            language: $("#languages").val(),
	            code: editor.getSession().getValue()
	        },
	
	        success: function(result){
				var temp = result.split("$#$");
				variableArray = temp[1];
				result = temp[0];
	            $(".highlight_editor").html(result);
	            $(".output").html("Code Highlited Successfully!");
				addVariableBox();
	        },

	        error: function(data) {
	            alert('woops highlight code!');
	        } 
	
		});
}

function DisplayCode() {
	//alert($("#username"));
	$.ajax({
			url: 'checking', 
			type: 'GET', 
	
	        data: {
	            language: $("#languages").val(),
	            code: editor.getSession().getValue(),
				user_name : $("#username")
	        },
	
	        success: function(result){
				editor.setValue(result);
	            $(".output").html("Code Displayed Successfully!");
				HighlightCode();
	        },
	        error: function(data) {
	            alert('woops display code!');
	        } 
	
		});
	

 }


function executeCode() {
	var varNameArray = getVal_fromVarBox().join("|");
	var randInput = document.getElementById("RandInputCheckBox").checked;	
	var randCount = document.getElementById("RandInputCountBox").value;
	$(".output").html("Compiling the "+$("#languages").val()+" Code!!!!!!");
	$.ajax({
			url: 'CodeCompiler', 
			type: 'GET', 
	
	        data: {
	            language: $("#languages").val(),
	            code: editor.getSession().getValue(),
				varArray: varNameArray,
				randRequired: randInput,
				randCount : randCount
	        },
	
	        success: function(result){
	            //$(".highlight_editor").html(result);
	            $(".output").html(result);
	        },
	        error: function(data) {
	            alert('execution Failed:'+data);
	        } 
	
		});
		

/* 
	//PHP Code

    $.ajax({

        url: "/ide/app/compiler.php",

        method: "POST",

        data: {
            language: $("#languages").val(),
            code: editor.getSession().getValue()
        },

        success: function(response) {
            $(".output").text(response)
        }
    })
*/

}