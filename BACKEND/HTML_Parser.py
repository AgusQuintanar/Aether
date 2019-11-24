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


def get_existing_urls():
    count = 0
    websites_path = "../WEBSITES/"
    existing_urls = set()
    websites_folders = [f.name for f in os.scandir(websites_path) if f.is_dir()]
    for website_folder in websites_folders:
        existing_urls.add(get_url_from_website_folder(websites_path+website_folder))
        if int(website_folder) > count:
            count = int(website_folder)
    return [existing_urls, count]


def get_url_from_website_folder(website_folder):
    with open(website_folder+"/website.metadata", "r") as website_metadata_file:
        website_metadata_file.readline()
        metadata = website_metadata_file.readline().split("!---!")
    return metadata[0] 


def get_urls_from_search(searchPhrase, existing_urls, count):
    new_urls = existing_urls
    new_count = count
    from googlesearch import search
    for url in search(searchPhrase, stop=20):
        if url not in new_urls:
            new_count += 1
            new_urls.add(url)
            addWebsite(url, new_count)

    return [new_urls, new_count]

def main():
    existing_urls_data = get_existing_urls()

    existing_urls = existing_urls_data[0]
    count = existing_urls_data[1]

    search_phrases = ['perros']

    for search_phrase in search_phrases:
        existing_urls_data = get_urls_from_search(search_phrase, existing_urls, count)
        existing_urls = existing_urls_data[0]
        count = existing_urls_data[1]

main()


