import requests
from bs4 import BeautifulSoup
import re
import os
import datetime


def create_website_files(count, url, metadata):
    websites_path = "../WEBSITES/"

    os.mkdir(websites_path+str(count)) 

    with open(websites_path+str(count)+"/website.metadata", "w") as file:
        file.write("publicUrl!---!privateUrl!---!TITLE!---!KEYWORDS!---!LINKS_TO!---!CREATED!---!VISITORS!---!RANK\n")
        file.write(url+'!---!'+url+'!---!'+metadata['title']+'!---!'+str(metadata['keywords'])+'!---!'+str(metadata['outgoing_links'])+'!---!'+
        datetime.date.today().strftime("%B %d, %Y")+'!---!'+str(0)+'!---!'+str(0.0))
        file.close()

    with open(websites_path+str(count)+"/metaDescription", "w") as file:
        file.write(metadata['description'])
        file.close()

def addWebsite(url, count):
    metadata = extract_metadata(url)
    create_website_files(count, url, metadata)

def extract_metadata(url):
    ATTRIBUTES = ['description', 'keywords', 'Description', 'Keywords']

    metadata = {"title" : "", "description" : "", "keywords" : "", "outgoing_links" : "",}
    try:
        r = requests.get(url)
    except Exception as e:
        print('Could not load page {}. Reason: {}'.format(url, str(e)))
    if r.status_code == 200:
        soup = BeautifulSoup(r.content, 'html.parser')
        metadata["title"] =  soup.title.text
        meta_list = soup.find_all("meta")
        for meta in meta_list:
            if 'name' in meta.attrs:
                name = meta.attrs['name']
                if name in ATTRIBUTES:
                    metadata[name.lower()] = meta.attrs['content']

        links = []

        for link in soup.findAll('a', attrs={'href': re.compile("^http://")}):
            links.append(link.get('href'))

        for link in soup.findAll('a', attrs={'href': re.compile("^https://")}):
            links.append(link.get('href'))

        metadata["outgoing_links"] = links

    else:
        print('Could not load page {}.Reason: {}'.format(url, r.status_code))

    return metadata




# links = ['http://www.astro.com']

# addPages(links,12)




