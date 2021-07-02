import React from "react";
import { Container, createMuiTheme, CssBaseline, makeStyles, Paper, ThemeProvider, useMediaQuery } from "@material-ui/core";
import Home from "./Home";

const useStyles = makeStyles((theme) => ({
    root: {
        margin: theme.spacing(5),
        padding: theme.spacing(3)
    }
}));

const App = () => {
    const prefersDarkMode = useMediaQuery("(prefers-color-scheme: dark)");

    const theme = React.useMemo(
        () => createMuiTheme({
            palette: {
                //type: prefersDarkMode ? "dark" : "light"
                type: "light"
            }
        }),
        [prefersDarkMode]
    );

    const classes = useStyles();

    return (
        <ThemeProvider theme={theme}>
            <CssBaseline />
            <Container>
                <Paper className={classes.root} elevation={3}>
                    <Container>
                        <Home />
                    </Container>
                </Paper>
            </Container>
        </ThemeProvider >
    );
}

export default App;
