<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>SE1 - Parkhaus</title>
    <style><%@include file="WEB-INF/style.css"%></style>
    <style> <%@include file="WEB-INF/lib/css/style.css"%></style>
    <style><%@include file="WEB-INF/lib/css/navbar.css"%></style>
    <style><%@include file="WEB-INF/lib/css/head.css"%></style>
    <html><%@include file="WEB-INF/lib/html/head.html"%></html>
    <!-- <html><%@include file="WEB-INF/lib/html/investor.html"%></html> -->
    <html><%@include file="WEB-INF/lib/html/navbar.html"%></html></style>
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
                  "extra_buttons":["Summe", "Durchschnitt", "habenVerlassen", "Auslastung", "ROI", "Manager"],
                  "extra_charts":["Diagramm", "FahrzeugtypenDiagramm", "AuslastungDiagramm", "Umsatzdiagramm"],
                  "vehicle_types":["Limousine", "Kombi", "SUV"],
                  "delay": 100
                  }'>
</ccm-parkhaus-10-2-3>


</body>
</html>
