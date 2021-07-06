import React from "react";
import { Fab, makeStyles, useTheme, Zoom } from "@material-ui/core";
import { Delete } from "@material-ui/icons";
import axios from "axios";

const useStyles = makeStyles((theme) => ({
    fab: {
        position: "fixed",
        bottom: theme.spacing(2),
        right: theme.spacing(2),
        margin: theme.spacing(1),
        width: "120px"
    }
}));

const ResetButton = () => {
    const classes = useStyles();
    const theme = useTheme();

    const transitionDuration = {
        enter: theme.transitions.duration.enteringScreen,
        exit: theme.transitions.duration.leavingScreen
    };

    const [showResetButton, setShowResetButton] = React.useState(true);
    const [showConfirmation, setShowConfirmation] = React.useState(false);

    const sendReset = () => {
        axios.get("./api/?cmd=reset").then(() => {
            window.location.reload();
        });
    };

    const handleClick = () => {
        if (showResetButton) {
            setShowResetButton(false);
            setShowConfirmation(true);
            setTimeout(() => {
                setShowConfirmation(false);
                setShowResetButton(true);
            }, 5000);
        } else {
            sendReset();
        }
    };


    return (
        <div>
            <Zoom
                in={showResetButton}
                timeout={transitionDuration}
                style={{
                    transitionDelay: `${showResetButton ? transitionDuration.exit : 0}ms`
                }}
            >
                <Fab
                    className={classes.fab}
                    color="secondary"
                    size="large"
                    variant="extended"
                    onClick={handleClick}
                >
                    <Delete /> Reset
                </Fab>
            </Zoom>
            <Zoom
                in={showConfirmation}
                timeout={transitionDuration}
                style={{
                    transitionDelay: `${showConfirmation ? transitionDuration.exit : 0}ms`
                }}
            >
                <Fab
                    className={classes.fab}
                    color="secondary"
                    size="large"
                    variant="extended"
                    onClick={handleClick}
                >
                    Sicher?
                </Fab>
            </Zoom>
        </div>
    );
};

export default ResetButton;