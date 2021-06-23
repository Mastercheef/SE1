<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>SE1 - Parkhaus</title>
    <style><%@include file="WEB-INF/style.css"%></style>
    <script src="https://ccmjs.github.io/mkaul-components/parkhaus/versions/ccm.parkhaus-10.2.3.js"></script>
    <script>
        var resetPopup = document.getElementById('resetPopup');
        window.onclick = function(event) {
            if (event.target == resetPopup) {
                resetPopup.style.display = "none";
            }
        }
    </script>
</head>
<body>
<!-- https://www.w3schools.com/howto/howto_css_delete_modal.asp -->
<button onclick="document.getElementById('resetPopup').style.display='block'">RESET</button>
<div id="resetPopup" class="resetPopup">
    <span onclick="document.getElementById('resetPopup').style.display='none'" class="close" title="Abbrechen">×</span>
    <form class="resetContent" action="./api/" method="get">
        <div class="container">
            <h1>Reset</h1>
            <p>Bist du sicher?</p>

            <div class="clearfix">
                <input type="hidden" name="cmd" value="reset" />
                <button type="button" onclick="document.getElementById('resetPopup').style.display='none'" class="cancelbtn">Cancel</button>
                <button type="submit" onclick="document.getElementById('resetPopup').style.display='none'" class="deletebtn">Reset</button>
            </div>
        </div>
    </form>
</div>

<ccm-parkhaus-10-2-3
        key='{"name":"CarHome",
                  "server_url":"./api/",
                  "extra_buttons":["Summe", "Durchschnitt", "habenVerlassen", "Auslastung"],
                  "extra_charts":["Diagramm", "FahrzeugtypenDiagramm", "AuslastungDiagramm"],
                  "vehicle_types":["Limousine", "Kombi", "SUV"]
                  }'>
</ccm-parkhaus-10-2-3>


</body>
</html>
