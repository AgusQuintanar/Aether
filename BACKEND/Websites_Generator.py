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
        file.write(url.strip().strip('\n')+'!---!'+url.strip().strip('\n')+'!---!'+metadata['title']+'!---!'+str(metadata['title']).strip().strip('\n')+
        ','+str(metadata['keywords']).strip().strip('\n')+'!---!'+str(metadata['outgoing_links']).strip().strip('\n')+'!---!'+
        datetime.date.today().strftime("%B %d, %Y")+'!---!'+str(0)+'!---!'+str(0.0))
        file.close()

    with open(websites_path+str(count)+"/metaDescription.txt", "w") as file:
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
 
        if r.status_code == 200:
            soup = BeautifulSoup(r.content, 'html.parser')
            metadata["title"] =  str(soup.title.text).strip().strip('\n')
            meta_list = soup.find_all("meta")
            for meta in meta_list:
                if 'name' in meta.attrs:
                    name = meta.attrs['name']
                    if name in ATTRIBUTES:
                        metadata[name.lower()] = meta.attrs['content']

            links = []

            for link in soup.findAll('a', attrs={'href': re.compile("^http://")}):
                links.append(str(link.get('href')).strip().strip('\n'))

            for link in soup.findAll('a', attrs={'href': re.compile("^https://")}):
                links.append(str(link.get('href')).strip().strip('\n'))

            metadata["outgoing_links"] = links

        else:
            print('Could not load page {}.Reason: {}'.format(url, r.status_code))
    except Exception as e:
        print('Could not load page {}. Reason: {}'.format(url, str(e)))

    return metadata


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
    try:
        with open(website_folder+"/website.metadata", "r") as website_metadata_file:
            website_metadata_file.readline()
            metadata = website_metadata_file.readline().split("!---!")
        return metadata[0] 
    except:
        return ""


def get_urls_from_search(searchPhrase, existing_urls, count):
    new_urls = existing_urls
    new_count = count
    from googlesearch import search
    for url in search(searchPhrase, stop=15):
        if url not in new_urls:
            new_count += 1
            new_urls.add(url)
            addWebsite(url, new_count)

    return [new_urls, new_count]

def get_outgoing_urls():
    websites_path = "../WEBSITES/"

    count = get_existing_urls()[1]
    

    outgoing_urls = set()
    
    for website in range(count-10):
        print(count)
        try:
            with open(websites_path+str(website)+"/website.metadata", "r") as file:
                
                cont = 0
                for line in file.readlines():
                    if cont == 1:
                        print(line.split('!---!')[4].split(','))
                        websites = line.split('!---!')[4].split(',')
                        for ws in websites:
                            outgoing_urls.add(str(ws))
                    cont += 1
        except Exception as e:
            continue
           
    return outgoing_urls



def main():
    existing_urls_data = get_existing_urls()

    existing_urls = existing_urls_data[0]
    count = existing_urls_data[1]

    search_phrases = []

    with open ("relatedQueries.csv",'r') as file:
        for line in file.readlines():
            if line != " " and "," in line:
                search_phrases.append(line.split(',')[0])

    print(search_phrases)

    #outgoing_urls = get_outgoing_urls()


    for search_phrase in search_phrases:
        try:
            existing_urls_data = get_urls_from_search(search_phrase, existing_urls, count)
            existing_urls = existing_urls_data[0]
            count = existing_urls_data[1]
        except Exception as e:
            print("Error while loading page"+str(e))



main()