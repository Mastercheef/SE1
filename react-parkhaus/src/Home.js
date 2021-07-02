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
        "\"client_categories\":[\"Standard\",\"Abo-1\",\"Abo-2\"]," +
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
                setDiagramError(true);
                setDiagramResult(`${text}: Fehler!`);
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
                        {(!diagramError && diagramResult) && (
                            <PlotComponent
                                data={diagramResult.data}
                            />
                        )}
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