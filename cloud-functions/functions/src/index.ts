import * as functions from 'firebase-functions'

export const sum = functions.https.onCall(async data => {
    return {
        sum: data.a + data.b
    }
})
