class ImportServiceImpl

    def self.find_ean(text)
      ean_pattern = /\b\d{13}\b/
      matches = text.scan(ean_pattern)
      matches.empty? ? "No EAN found" : matches
    end

  end

importServiceImpl = ImportServiceImpl.new()  
Polyglot.export('importServiceImpl', importServiceImpl)