
from flask import Flask, request, json
import shelve


with shelve.open("high_scores") as db:
    if(not "games" in db):
        db["games"] = []

errorMessage = "error"

app = Flask(__name__)

@app.route('/', methods=["GET", "POST"])
def processing():

    try:
        data = json.loads(request.data)
    except Exception:
        return errorMessage

    if(not "type" in data or not data["type"] in ["add", "get", "clear"]):
        return errorMessage

    if(data["type"]=="clear"):
        with shelve.open("high_scores") as db:
            db["games"] = []
        return "ok"

    if(data["type"]=="add"):
        if(not "name" in data or not "score" in data or not "levelID"):
            return errorMessage

        try:
            score = int(data["score"])
            levelID = int(data["levelID"])
        except Exception:
            return errorMessage

        with shelve.open("high_scores") as db:
            db["games"] = db["games"] + [[data["name"], score, levelID]]

        return "ok"

    if(data["type"]=="get"):
        if(not "count" in data):
            return errorMessage

        try:
            count = int(data["count"])
        except Exception:
            return errorMessage


        with shelve.open("high_scores") as db:
            scores = db["games"]
        scores.sort(key = lambda v: v[1] * 1000000 - v[2])

        scores = ["%s:%d:%d" % (i[0], i[1], i[2]) for i in scores[:min(count, len(scores))]]
        return "|".join(scores)

    return str(request.data)
