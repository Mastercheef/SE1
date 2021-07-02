import React from "react";
import axios from "axios";
import { Button, Divider, Grid, makeStyles, Typography } from "@material-ui/core";
import { DataGrid } from "@material-ui/data-grid";
import PlotComponent from "./PlotComponent";

const useStyles = makeStyles((theme) => ({
    parkhausContainer: {
        width: "100%"
    },
    dividerContainer: {
        marginTop: theme.spacing(2),
        marginBottom: theme.spacing(2),
        width: "100%"
    },
    tableContainer: {
        height: "400px",
        width: "100%"
    }
}));

const cmdButtons = [
    ["Summe", "Summe"],
    ["Ø Ticketpreis", "Durchschnitt"],
    ["Verkäufe", "habenVerlassen"],
    ["Aktuelle Auslastung", "Auslastung"]
];

const diagramButtons = [
    ["Tickets", "Diagramm"],
    ["Fahrzeugtypen", "FahrzeugtypenDiagramm"],
    ["Auslastung", "AuslastungDiagramm"],
    ["Kundentypen", "KundentypenDiagramm"],
    ["Ø Parkdauer Abonnenten", "AboParkdauerDiagramm"]
];

const Home = () => {
    const classes = useStyles();

    const parkhaus = "<ccm-parkhaus-10-2-3 " +
        "key='{\"name\":\"CarHome\"," +
        "\"server_url\":\"./api/\"," +
        "\"extra_charts\":[\"Diagramm\", \"FahrzeugtypenDiagramm\", \"AuslastungDiagramm\", \"KundentypenDiagramm\", \"AboParkdauerDiagramm\"],\"client_categories\":[\"Standard\",\"Abo-1\",\"Abo-2\"]," +
        "\"vehicle_types\":[\"Limousine\", \"Kombi\", \"SUV\"]," +
        "\"delay\": 100," +
        "\"hide_table\": true}'>" +
        "</ccm-parkhaus-10-2-3>";

    const columns = [
        { field: "nr", headerName: "Nr", width: 100 }
    ];

    const rows = [];

    const [diagramError, setDiagramError] = React.useState(false);
    const [diagramResult, setDiagramResult] = React.useState();

    const [cmdError, setCmdError] = React.useState(false);
    const [cmdResult, setCmdResult] = React.useState("Klicke einen Button an");

    const getCmd = (text, cmd, isDiagram) => {
        axios.get(`./api/?cmd=${cmd}`).then((response) => {
            if (isDiagram) {
                setDiagramError(false);
                setDiagramResult(response.data);
            } else {
                setCmdError(false);
                setCmdResult(`${text}: ${response.data}`);
            }
        }).catch(() => {
            if (isDiagram) {
                /*setDiagramError(true);
                setDiagramResult(`${text}: Fehler!`);*/
                setDiagramError(false);
                setDiagramResult({ "data": [{ "x": ["07-01 02:01:48:912", "07-01 02:01:49:614", "07-01 02:01:50:21", "07-01 02:01:50:620", "07-01 02:01:50:821", "07-01 02:01:52:17", "07-01 02:01:52:214", "07-01 02:01:52:427", "07-01 02:01:52:614", "07-01 02:01:52:820", "07-01 02:02:15:725", "07-01 02:02:16:943", "07-01 02:02:17:316", "07-01 02:02:17:534", "07-01 02:02:18:120", "07-01 02:02:31:401"], "y": ["207.0", "400.0", "800.0", "800.0", "759.4", "698.6666666666666", "855.4285714285714", "923.5", "909.6666666666666", "1345.1", "3614.4545454545455", "3396.25", "3396.25", "3396.25", "3166.5384615384614", "3902.6428571428573"], "type": "line", "name": "Durchschnittl. Parkdauer der Abonnenten" }], "layout": { "title": { "text": "Durschnittliche Parkdauer der Abonnenten" }, "xaxis": { "title": { "text": "Zeitpunkt der Messung" } }, "yaxis": { "title": { "text": "Parkdauer (in ms)" } } } });
            } else {
                setCmdError(true);
                setCmdResult(`${text}: Fehler!`);
            }
        });
    };

    return (
        <Grid
            container
            direction="column"
            justify="center"
            alignItems="center"
        >
            <Grid
                className={classes.parkhausContainer}
                item
                dangerouslySetInnerHTML={{ __html: parkhaus }}
            />
            <Grid className={classes.dividerContainer} item>
                <Divider variant="fullWidth" />
            </Grid>
            <Grid item>
                <Grid
                    container
                    direction="column"
                    justify="center"
                    alignItems="center"
                    spacing={3}
                >
                    <Grid item>
                        <Typography
                            color={cmdError ? "error" : "inherit"}
                            variant="h6"
                        >
                            {cmdResult}
                        </Typography>
                    </Grid>
                    <Grid item>
                        <Grid
                            container
                            direction="row"
                            justify="center"
                            alignItems="center"
                            spacing={3}
                        >
                            {cmdButtons.map((btn, index) => {
                                return (
                                    <Grid key={`cmdButton-${index}`} item>
                                        <Button
                                            color="primary"
                                            variant="contained"
                                            onClick={() => getCmd(btn[0], btn[1], false)}
                                        >{btn[0]}</Button>
                                    </Grid>
                                );
                            })}
                        </Grid>
                    </Grid>
                </Grid>
            </Grid>
            <Grid className={classes.dividerContainer} item>
                <Divider variant="fullWidth" />
            </Grid>
            <Grid item>
                <Grid
                    container
                    direction="column"
                    justify="center"
                    alignItems="center"
                    spacing={3}
                >
                    <Grid item>
                        {diagramError && (
                            <Typography
                                color="error"
                                variant="h6"
                            >
                                {diagramResult}
                            </Typography>
                        )}
                        <PlotComponent
                            data={diagramResult.data}
                        />
                    </Grid>
                    <Grid item>
                        <Grid
                            container
                            direction="row"
                            justify="center"
                            alignItems="center"
                            spacing={3}
                        >
                            {diagramButtons.map((btn, index) => {
                                return (
                                    <Grid key={`diagramButton-${index}`} item>
                                        <Button
                                            color="primary"
                                            variant="contained"
                                            onClick={() => getCmd(btn[0], btn[1], true)}
                                        >{btn[0]}</Button>
                                    </Grid>
                                );
                            })}
                        </Grid>
                    </Grid>
                </Grid>
            </Grid>
            <Grid className={classes.dividerContainer} item>
                <Divider variant="fullWidth" />
            </Grid>
            <Grid className={classes.tableContainer} item>
                <DataGrid rows={rows} columns={columns} />
            </Grid>
        </Grid>
    );
}

export default Home;