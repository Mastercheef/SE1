import React from "react";
import axios from "axios";
import { Button, Divider, Grid, makeStyles, Typography } from "@material-ui/core";
import { DataGrid, deDE } from "@material-ui/data-grid";
import PlotComponent from "./PlotComponent";
import moment from "moment";

moment.locale("de");

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
        height: "650px",
        width: "100%"
    }
}));

const cmdButtons = [
    ["Summe", "Summe", "€"],
    ["Ø Ticketpreis", "Durchschnitt", "€"],
    ["Gedruckte Tickets", "habenVerlassen"],
    ["Aktuelle Auslastung", "Auslastung"]
];

const diagramButtons = [
    ["Tickets", "Diagramm"],
    ["Fahrzeugtypen", "FahrzeugtypenDiagramm"],
    ["Auslastung", "AuslastungDiagramm"],
    ["Kundentypen", "KundentypenDiagramm"],
    ["Ø Parkdauer Abonnenten", "AboParkdauerDiagramm"]
];

const momentTimeFormat = "DD.MM.YY HH:mm:ss";
const momentDurationFormat = "HH:mm:ss";

const Home = () => {
    const classes = useStyles();

    const columns = [
        { field: "nr", headerName: "Nr", width: 75 },
        { field: "arrival", headerName: "Ankunft", width: 200 },
        { field: "departure", headerName: "Abfahrt", width: 200 },
        { field: "duration", headerName: "Dauer", width: 100 },
        { field: "licensePlate", headerName: "Kennzeichen", width: 140 },
        { field: "vehicleType", headerName: "Fahrzeugtyp", width: 140 },
        { field: "customerType", headerName: "Kundentyp", width: 130 },
        { field: "price", headerName: "Preis", width: 90 },
        { field: "ticketId", headerName: "Ticket ID", width: 300 }
    ];

    const [diagramError, setDiagramError] = React.useState(false);
    const [diagramResult, setDiagramResult] = React.useState();

    const [cmdError, setCmdError] = React.useState(false);
    const [cmdResult, setCmdResult] = React.useState("Klicke einen Button an");

    const [rows, setRows] = React.useState([]);

    const updateRows = (id) => {
        axios.get(`./api/?cmd=ticket&id=${id}`).then((response) => {
            let windowRows;
            if (window.rows) {
                windowRows = window.rows;
            } else {
                windowRows = [];
            }
            const newRows = windowRows.concat({
                id: windowRows.length + 1,
                nr: response.data.nr,
                arrival: moment(response.data.arrival).format(momentTimeFormat),
                departure: moment(response.data.departure).format(momentTimeFormat),
                duration: moment.utc(moment.duration(response.data.duration).asMilliseconds()).format(momentDurationFormat),
                licensePlate: response.data.licensePlate,
                vehicleType: response.data.vehicleType,
                customerType: response.data.customerType,
                price: `${response.data.price} €`,
                ticketId: response.data.ticketId
            });
            window.rows = newRows;
            setRows(newRows);
        });
    };

    const parkhausPost = (request) => {
        const params = request.split(",");
        if (params[0] === "leave") {
            updateRows(params[5]);
        }
    };

    React.useEffect(() => {
        window.parkhausPost = parkhausPost;
    }, []);

    const parkhaus = "<ccm-parkhaus-10-2-3 " +
        "key='{\"name\":\"CarHome\"," +
        "\"server_url\":\"./api/\"," +
        "\"client_categories\":[\"Standard\",\"Abo-1\",\"Abo-2\"]," +
        "\"vehicle_types\":[\"Limousine\", \"Kombi\", \"SUV\"]," +
        "\"delay\": 100," +
        "\"hide_table\": true," +
        "\"css\": [\"ccm.load\",\"./css/parkhaus.css\"]," +
        "\"images\":{" +
            "\"car\":\"./img/car.png\"," +
            "\"parking_garage\":\"./img/parkhaus.png\"," +
            "\"empty\":\"./img/space.png\"" +
        "}}'>" +
        "</ccm-parkhaus-10-2-3>";

    const getCmd = (text, cmd, unit, isDiagram) => {
        axios.get(`./api/?cmd=${cmd}`).then((response) => {
            if (isDiagram) {
                setDiagramError(false);
                setDiagramResult(response.data);
            } else {
                setCmdError(false);
                if (unit) {
                    setCmdResult(`${text}: ${response.data} ${unit}`);
                } else {
                    setCmdResult(`${text}: ${response.data}`);
                }
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
                                            onClick={() => getCmd(btn[0], btn[1], btn[2], false)}
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
                                            onClick={() => getCmd(btn[0], btn[1], null, true)}
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
                <DataGrid
                    disableColumnMenu={true}
                    disableSelectionOnClick={true}
                    pageSize={10}
                    rowsPerPageOptions={[10, 25, 50, 100]}
                    localeText={deDE.props.MuiDataGrid.localeText}
                    rows={rows}
                    columns={columns}
                />
            </Grid>
        </Grid>
    );
}

export default Home;