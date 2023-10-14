
import array
import requests
import re
import csv
import datetime
import whois
import urllib3
import time
import http.client
import dns.resolver
import socket
import idna
import joblib
import numpy as np

from link_feature import Check_link


def Link_Scan_GBM(link):
    test_link = "https://l.wl.co/l?u=https://bit.ly/3sUwbpH?trackingid=XPvBQDEy&signature=newsletter"
    feature_vector = Check_link(link)


    feature_vector_2d = np.array(feature_vector).reshape(1, -1)


    model = joblib.load("C:\\Users\\dlaej\\Downloads\\Dataset_Capstone\\Code\\python_server\\GBM_model.pkl")


    predicted_label = model.predict(feature_vector_2d)
    

    if predicted_label == 1:
        return 1
    else:
        return 0
    
# test_link = "https://l.wl.co/l?u=https://bit.ly/3sUwbpH?trackingid=XPvBQDEy&signature=newsletter"
# feature_vector = Check_link(test_link)


# feature_vector_2d = np.array(feature_vector).reshape(1, -1)


# model = joblib.load("C:\\Users\\dlaej\\Downloads\\Dataset_Capstone\\Code\\python_server\\RF.pkl")


# predicted_label = model.predict(feature_vector_2d)
# print(predicted_label)

# if (predicted_label) == 1:
#     print(1)

# elif(predicted_label)==0:
#     print(0)