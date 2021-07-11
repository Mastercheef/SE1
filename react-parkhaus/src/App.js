/**
 * @author: ecetin2s
 */

import React from "react";
import { Container, createMuiTheme, CssBaseline, Grid, makeStyles, Paper, ThemeProvider, useMediaQuery } from "@material-ui/core";
import Home from "./Home";
import ResetButton from "./ResetButton";
import { red } from "@material-ui/core/colors";
import Investor from "./Investor";
import Manager from "./Manager";

const useStyles = makeStyles((theme) => ({
    container: {
        [theme.breakpoints.up("lg")]: {
            maxWidth: "1450px"
        },
        marginTop: theme.spacing(5),
        marginBottom: theme.spacing(5)
    },
    paper: {
        padding: theme.spacing(2)
    },
    sameHeight: {
        height: "470px",
        padding: theme.spacing(2)
    }
}));

const App = () => {
    const prefersDarkMode = useMediaQuery("(prefers-color-scheme: dark)");

    const theme = React.useMemo(
        () => createMuiTheme({
            palette: {
                type: prefersDarkMode ? "dark" : "light",
                secondary: {
                    main: red["800"]
                }
            }
        }),
        [prefersDarkMode]
    );

    const classes = useStyles();

    return (
        <ThemeProvider theme={theme}>
            <CssBaseline />
            <Container className={classes.container}>
                <Grid
                    container
                    direction="row"
                    justifyContent="center"
                    alignItems="center"
                    spacing={3}
                >
                    <Grid item sm={12}>
                        <Paper className={classes.paper}>
                            <Home />
                        </Paper>
                    </Grid>
                    <Grid item sm={12} md={6}>
                        <Paper className={classes.sameHeight}>
                            <Investor />
                        </Paper>
                    </Grid>
                    <Grid item sm={12} md={6}>
                        <Paper className={classes.sameHeight}>
                            <Manager />
                        </Paper>
                    </Grid>
                </Grid>
                <ResetButton />
            </Container>
        </ThemeProvider>
    );
}

export default App;
