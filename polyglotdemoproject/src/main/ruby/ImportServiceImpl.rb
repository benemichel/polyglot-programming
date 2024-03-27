class ImportServiceImpl

    def find_ean(text)
      ean_pattern = /\b\d{13}\b/
      matches = text.scan(ean_pattern)
      matches.empty? ? "No EAN found" : matches[0]
    end

  end

importServiceImpl = ImportServiceImpl.new()