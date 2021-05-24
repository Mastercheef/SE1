<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>SE1 - Parkhaus</title>
    <script src='https://cdn.plot.ly/plotly-latest.min.js'></script>
    <script src='https://ccmjs.github.io/mkaul-components/parkhaus/versions/ccm.parkhaus-9.1.9.js'></script>
  </head>
  <body>
    <ccm-parkhaus-9-1-9
            key='{"name":"CarHome",
                  "server_url":"http://localhost:8080/Parkhaus_war_exploded/api/",
                  "extra_buttons":["Summe", "Durchschnitt", "habenVerlassen"],
                  "extra_charts":["Diagramm", "FahrzeugtypenDiagramm"],
                  "client_categories":["Limousine", "Kombi", "SUV"]
                  }'>
    </ccm-parkhaus-9-1-9>
  </body>
</html>
