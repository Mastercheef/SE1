import React from "react";
import { Container, createMuiTheme, CssBaseline, makeStyles, Paper, ThemeProvider, useMediaQuery } from "@material-ui/core";
import Home from "./Home";

const useStyles = makeStyles((theme) => ({
    container: {
        [theme.breakpoints.up("md")]: {
            maxWidth: "90%"
        }
    },
    homePaper: {
        margin: theme.spacing(3),
        paddingTop: theme.spacing(4),
        paddingBottom: theme.spacing(4),
        minWidth: "1000px"
    }
}));

const App = () => {
    const prefersDarkMode = useMediaQuery("(prefers-color-scheme: dark)");

    const theme = React.useMemo(
        () => createMuiTheme({
            palette: {
                type: prefersDarkMode ? "dark" : "light"
            }
        }),
        [prefersDarkMode]
    );

    const classes = useStyles();

    return (
        <ThemeProvider theme={theme}>
            <CssBaseline />
            <Container className={classes.container}>
                <Paper className={classes.homePaper} elevation={3}>
                    <Container className={classes.container}>
                        <Home />
                    </Container>
                </Paper>
            </Container>
        </ThemeProvider >
    );
}

export default App;
